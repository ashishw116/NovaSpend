package dev.ashishwagh.novaspend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.model.Status;
import dev.ashishwagh.novaspend.model.User;
import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository{
	private final  MongoTemplate mongoTemplate;
	@Override
	public Page<UserResponse> filterUsers(int page, int size, Status status, LocalDateTime fromDate, LocalDateTime toDate) {
		Query query=new Query();
		if(status!=null)
			query.addCriteria(Criteria.where("status").is(status));
		if(fromDate!=null||toDate!=null)
		{
			LocalDateTime startDate;
			LocalDateTime endDate;
			if(fromDate==null)
			{
				startDate=toDate.minusYears(1);
				endDate=toDate;
			}
			else if(toDate==null)
			{
				startDate=fromDate;
				endDate=LocalDateTime.now();
			}
			else
			{
				startDate=fromDate;
				endDate=toDate;
			}
			if(startDate.isAfter(endDate))
				throw new IllegalArgumentException("fromDate cannot be after toDate");
			query.addCriteria(Criteria.where("createdAt").gte(startDate).lte(endDate));
		}
		query.fields()
        .include("name")
        .include("email")
        .include("role")
        .include("status")
        .include("createdAt");
		Pageable pageable=PageRequest.of(page, size,Sort.by(Sort.Direction.DESC,"createdAt"));
		long total=mongoTemplate.count(query,User.class);
		query.with(pageable);
		List<UserResponse> results=mongoTemplate.find(query, UserResponse.class);
		return new PageImpl<>(results,pageable,total);
	}

}
