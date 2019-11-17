--使用PL/SQL建表
BEGIN
FOR i IN 1..31 LOOP
EXECUTE IMMEDIATE
'CREATE TABLE e_detail_'||TO_CHAR(i)||
'(
name varchar2(20),
srcId varchar2(5),
desId varchar2(5),
devId varchar2(5),
sersorAddress varchar2(7),
count number(2),
cmd  varchar2(5),
status number(2),
data number(9,4),
gather_date date
)';
END LOOP;
END;
/

--使用PL/SQL删除表
BEGIN
FOR i IN 1..31 LOOP
EXECUTE IMMEDIATE
'DROP TABLE e_detail_'||TO_CHAR(i);
END LOOP;
END;
/
