/*
insert into tbl_databases (db_id, db_name, db_driver, created_by, created_at) values (1, 'MY SQL', 'com.mysql.cj.jdbc.Driver', 'SYSTEM', now());
insert into tbl_databases (db_id, db_name, db_driver, created_by, created_at) values (2, 'ORACLE', 'com.mysql.cj.jdbc.Driver', 'SYSTEM', now());

insert into tbl_charts (chart_id, chart_name, chart_desc, created_by, created_at) values (1, 'LINE', 'LINE', 'SYSTEM', now());
insert into tbl_charts (chart_id, chart_name, chart_desc, created_by, created_at) values (2, 'BAR', 'BAR', 'SYSTEM', now());
insert into tbl_charts (chart_id, chart_name, chart_desc, created_by, created_at) values (3, 'PIE', 'PIE', 'SYSTEM', now());

insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (1, 'labels', true, 1, 'SYSTEM', now());
insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (2, 'dataset_label', true, 1, 'SYSTEM', now());
insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (3, 'data', true, 1, 'SYSTEM', now());

insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (4, 'labels', true, 2, 'SYSTEM', now());
insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (5, 'dataset_label', true, 2, 'SYSTEM', now());
insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (6, 'data', true, 2, 'SYSTEM', now());

insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (7, 'labels', true, 3, 'SYSTEM', now());
insert into tbl_chart_attributes (attr_id, attr_key, attr_validation, attr_chart_id, created_by, created_at) values (8, 'data', true, 3, 'SYSTEM', now());
*/