package dev.ashishwagh.novaspend.repository;

import java.time.LocalDate;
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

import dev.ashishwagh.novaspend.dto.FinanceEntryResponseProjection;
import dev.ashishwagh.novaspend.model.EntryType;
import dev.ashishwagh.novaspend.model.FinanceEntry;
import lombok.RequiredArgsConstructor;
@Repository
@RequiredArgsConstructor
public class FinanceEntryCustomRepositoryImpl implements FinanceEntryCustomRepository{

	private final MongoTemplate mongoTemplate;
	@Override
	public Page<FinanceEntryResponseProjection> filterEntries(String userId, EntryType type, List<String> categories, LocalDate fromDate,
			LocalDate toDate, int page, int size) {
		Query query=new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		if(type!=null)
			query.addCriteria(Criteria.where("type").is(type));
		
		if(categories!=null&&!categories.isEmpty())
			query.addCriteria(Criteria.where("category").in(categories));
		
		if(fromDate!=null||toDate!=null)
		{
		    LocalDate startDate;
			LocalDate endDate;
			if(fromDate==null)
			{
				startDate=toDate.minusYears(1);
				endDate=toDate;
			}
			else if(toDate==null)
			{
				startDate=fromDate;
				endDate=LocalDate.now();
			}
			else
			{
				startDate=fromDate;
				endDate=toDate;
			}
 
			if(startDate.isAfter(endDate))
			    throw new IllegalArgumentException("fromDate cannot be after toDate");
			query.addCriteria(Criteria.where("date").gte(startDate).lte(endDate));
		}
		query.fields()
		.include("_id")
	    .include("amount")
	    .include("category")
	    .include("date")
	    .include("type");

		//sort
		Pageable pageable=PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"date"));
		//count
		long total=mongoTemplate.count(query, FinanceEntry.class);
		//pagination
		query.with(pageable);
		//execute
		List<FinanceEntryResponseProjection> results=mongoTemplate.find(query,FinanceEntryResponseProjection.class,mongoTemplate.getCollectionName(FinanceEntry.class));		
		return new PageImpl<>(results, pageable, total);
	}

}
