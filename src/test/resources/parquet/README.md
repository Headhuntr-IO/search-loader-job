# Unload Script

## Candidate
```sql
unload ( 
'select
     cand_id cand_id,
     first_name,
     last_name,
     full_name,
     ceil(total_yrs_exp*12) months_experience
 
 from cand_profile_na
 where cand_id in (select cand_id from lyndon_test_candidates)'
)
to 's3://hh-poc-glue-oregon/sample/candidates/'
iam_role 'arn:aws:iam::327229172692:role/HHDevGlueRole'
format parquet
```

## Candidate / Job History
```sql
unload (
'
select
    cand_id,
    job_seq job_sequence,
    company.id company_id,
    company.name company_name,
    job_title title,
    job_description description,
    date_job_start job_start,
    date_job_end job_end,
    work_loc work_location,
    ceil(yrs_exp * 12) months_experience

from job_hist_na left join (
    select (row_number() over (order by company_name) + 10000) id, company_name name, count(*) candidate_count
    from job_hist_na
    where company_name <> ''''
        and cand_id in (select cand_id from lyndon_test_candidates)
    group by company_name
    order by company_name asc
    ) company on company.name = job_hist_na.company_name
where cand_id in (select cand_id from lyndon_test_candidates)
'
)
to 's3://hh-poc-glue-oregon/sample/candidates_job_history/'
iam_role 'arn:aws:iam::327229172692:role/HHDevGlueRole'
format parquet
```

## Company
```sql
unload (
'
select (row_number() over (order by company_name) + 10000) id, company_name name, count(*) candidate_count
from job_hist_na
where company_name <> ''''
    and cand_id in (select cand_id from lyndon_test_candidates)
group by company_name
order by company_name asc
'
)
to 's3://hh-poc-glue-oregon/sample/company/'
iam_role 'arn:aws:iam::327229172692:role/HHDevGlueRole'
format parquet
```