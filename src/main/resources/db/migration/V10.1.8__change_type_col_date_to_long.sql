alter table assignments
alter column deadline_date type int
    USING deadline_date::integer;