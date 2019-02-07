-------- ***** spm_member ?Öå?ù¥Î∏?
create table spm_member
(member_idx number not null Primary key  -- ?öå?õêÎ≤àÌò∏
,userid varchar2(40) not null Unique -- ?öå?õê?ïÑ?ù¥?îî
,pwd varchar2(200) not null -- ?öå?õêÎπÑÎ?Î≤àÌò∏
,username varchar2(20) not null -- ?öå?õê?ù¥Î¶?
,post1 number(3) not null
,post2 number(3) not null
,addr1 varchar2(50) not null -- ?öå?õê Ï£ºÏÜå
,addr2 varchar2(50) not null -- ?öå?õê ?ÉÅ?Ñ∏ Ï£ºÏÜå
,email varchar2(200) not null -- ?öå?õê ?ù¥Î©îÏùº
,hp1 varchar2(3) not null -- ?öå?õê ?ú¥???è∞Î≤àÌò∏ 1
,hp2 varchar2(50) not null -- ?öå?õê ?ú¥???è∞Î≤àÌò∏ 2
,hp3 varchar2(50) not null -- ?öå?õê ?ú¥???è∞Î≤àÌò∏ 3
,gender number(1) not null
,birthday varchar(8) not null
,point number default 0 not null -- ?öå?õê ?è¨?ù∏?ä∏
,lastLoginDate date default sysdate not null -- ?öå?õê ÎßàÏ?Îß? ?†ë?Üç ?Ç†Ïß?
,lastPwdChangeDate date default sysdate not null -- ÎßàÏ?Îß? ÎπÑÎ?Î≤àÌò∏ Î≥?Í≤ΩÎÇ†Ïß?
,status number(1) default 1 not null -- ?öå?õê?ÉÅ?Éú(1:?†ï?ÉÅ?öå?õê,0:?Éà?á¥?öå?õê)
,constraints spm_member_point_ck check(point>=0)
,constraints spm_member_gender_ck check(gender in(1,2))
);
commit;
delete spm_member where userid='daiunii';
select*from spm_member;
alter table spm_member modify addr1 varchar(100);
alter table spm_member modify addr2 varchar(100);
alter table spm_member add registerdate date default sysdate;
commit;
alter table spm_member rename column meber_idx to member_idx;
------- ***** spm_member_idx_seq ?ãú???ä§
create sequence spm_member_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

--- ***
create table spm_category_1st
(cg_1st_idx number not null Primary Key
,cg_1st_code varchar2(2) not null Unique
,cg_1st_name varchar2(20) not null
);
------- ***** spm_cg_1st_idx_seq ?ãú???ä§
create sequence spm_cg_1st_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

-----****
create table spm_category_2nd
(cg_2nd_idx number not null Primary Key
,cg_2nd_code varchar2(3) not null Unique
,cg_2nd_name varchar2(20) not null
,fk_cg_1st_code varchar2(2) not null
,constraints spm_cg_1st_code_fk foreign Key (fk_cg_1st_code) references spm_category_1st(cg_1st_code) on cascade
);
alter table spm_category_2nd drop constraint spm_cg_1st_code_fk;
alter table spm_category_2nd add constraint spm_cg_1st_code_fk foreign key (fk_cg_1st_code) references spm_category_1st(cg_1st_code) on delete cascade;
commit;
------- ***** spm_cg_2nd_idx_seq ?ãú???ä§
create sequence spm_cg_2nd_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

------- ***** spm_product ?Öå?ù¥Î∏?
create table spm_product
(product_idx number not null Primary Key
,fk_cg_1st_code varchar2(2) not null
,fk_cg_2nd_code varchar2(3) not null
,pcode varchar2(8) not null Unique
,pname varchar2(20) not null
,mainimg1 varchar2(100) not null
,mainimg2 varchar2(100) not null
,pspec varchar2(20) not null
,price number not null
,pcompany varchar2(20) not null
,totalsqty number not null
,constraints spm_pd_cg_1st_code_fk foreign key (fk_cg_1st_code) references spm_category_1st(cg_1st_code)
,constraints spm_pd_cg_2nd_code_fk foreign key (fk_cg_2nd_code) references spm_category_2nd(cg_2nd_code)
);
alter table spm_product rename column psec to pspec;
------ **** spm_product_idx_seq ?ãú???ä§
create sequence spm_product_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

------ ***** spm_import
create table spm_import
(import_idx number not null Primary Key
,fk_stock_idx number not null
,iqty number not null
,importdate date default sysdate not null
,constraints spm_import_stock_idx_fk foreign key (fk_stock_idx) references spm_stock(stock_idx)
);

alter table spm_import drop constraint spm_import_stock_idx_fk;
alter table spm_import add constraint spm_import_stock_idx_fk foreign key (fk_stock_idx) references spm_stock(stock_idx) on delete cascade;
commit;
------ **** spm_import_idx
create sequence spm_import_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

----- ***
create table spm_order
(odrcode varchar2(20) not null Primary Key
,fk_userid varchar2(40) not null
,sumtotalprice number
,orderdate date default sysdate not null
,dvrfee number default 0 not null
,constraints spm_order_odrcode_fk foreign key (fk_userid) references spm_member(userid)
);

alter table spm_order drop constraint spm_order_odrcode_fk;
alter table spm_order add constraint spm_order_odrcode_fk foreign key (fk_userid) references spm_member(userid) on delete cascade;
commit;
----- ***
create sequence spm_order_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

--- ***
create table spm_review
(review_idx number not null Primary Key
,fk_userid varchar2(40) not null
,fk_pcode varchar2(8) not null
,rspec varchar2(20)
,starpoint number(2) 
,content varchar2(400) not null
,review_img varchar2(100)
,writedate date default sysdate not null
,constraints spm_review_userid_fk foreign key (fk_userid) references spm_member(userid)
,constraints spm_review_pcode_fk foreign key (fk_pcode) references spm_product(pcode)
);

--- ***
create sequence spm_review_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


--- ***
create table spm_memo
(memo_idx number not null Primary Key
,fk_pcode varchar2(8) not null
,fk_userid varchar2(40) not null
,content varchar(400) not null
,writedate date default sysdate not null
,constraints spm_memo_pcode_fk foreign key (fk_pcode) references spm_product(pcode)
,constraints spm_memo_userid_fk foreign key (fk_userid) references spm_member(userid)
);

--- ***
create sequence spm_memo_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

------ ***** spm_stock
create table spm_stock
(stock_idx number not null Primary Key
,fk_pcode varchar2(8) not null
,psize varchar2(20) default 'Free'
,color varchar2(20) default 'None'
,pqty number not null
,constraints spm_stock_pcode_fk foreign key (fk_pcode) references spm_product(pcode)
);

alter table spm_stock drop constraint spm_stock_pcode_fk;
alter table spm_stock add constraint spm_stock_pcode_fk foreign key (fk_pcode) references spm_product(pcode) on delete cascade;
commit;
------ **** spm_stock_idx
create sequence spm_stock_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

create table spm_order_detail
(order_detail_idx number not null Primary Key
,fk_odrcode varchar2(20) not null
,fk_pcode varchar2(8) not null
,fk_stock_idx number not null
,oqty number not null
,totalprice number not null
,dvrdate date
,dvrstatus number(1) default 0 not null
,constraints spm_odr_detail_odrcode_fk foreign key (fk_odrcode) references spm_order(odrcode)
,constraints spm_odr_detail_pcode_fk foreign key (fk_pcode) references spm_product(pcode)
,constraints spm_odr_detail_stock_idx_fk foreign key (fk_stock_idx) references spm_stock(stock_idx)
);

alter table spm_order_detail drop constraint spm_odr_detail_odrcode_fk;
alter table spm_order_detail drop constraint spm_odr_detail_pcode_fk;
alter table spm_order_detail drop constraint spm_odr_detail_stock_idx_fk;
alter table spm_order_detail add constraint spm_odr_detail_odrcode_fk foreign key (fk_odrcode) references spm_order(odrcode) on delete cascade;
alter table spm_order_detail add constraint spm_odr_detail_pcode_fk foreign key (fk_pcode) references spm_product(pcode) on delete cascade;
alter table spm_order_detail add constraint spm_odr_detail_stock_idx_fk foreign key (fk_stock_idx) references spm_stock(stock_idx) on delete cascade;
commit;
--- ***
create sequence spm_order_detail_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


-- category 1st ?ç∞?ù¥?Ñ∞Í∞? Ï∂îÍ?
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'10','outer');
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'20','top');
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'30','pants');
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'40','ops/skirt');
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'50','knit/cardigan');
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'60','shoe/bags/acc');
insert into spm_category_1st(cg_1st_idx,cg_1st_code,cg_1st_name)
values(spm_cg_1st_idx_seq.nextval,'70','leggings/socks');

select * from spm_category_2nd;

-- Outer , 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'001','?å®?î©/?†ê?çº','10');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'002','ÏΩîÌä∏/?ûêÏº?','10');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'003','Í∞??îîÍ±?','10');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'004','Ï°∞ÎÅº','10');

-- Top, 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'005','?ã∞?ÖîÏ∏?','20');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'006','Î∏îÎùº?ö∞?ä§,?ÖîÏ∏?','20');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'007','?ãà?ä∏','20');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'008','?Çò?ãú','20');

-- Pants , 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'009','?ä¨?ûô?ä§','30');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'010','?ç∞?ãò','30');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'011','?å¨Ï∏?','30');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'012','Î∞òÎ∞îÏß?','30');


-- OPS&Skirt, 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'013','Î¨¥Ï?OPS','40');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'014','?Çò?óºOPS','40');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'015','?ç∞?ãòOPS','40');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'016','?ãà?ä∏OPS','40');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'017','?ä§Ïª§Ìä∏','40');

-- knit&Cardigan, 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'018','?ÉÅ?ùò','50');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'019','Í∞??îîÍ±?','50');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'020','Ï°∞ÎÅº','50');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'021','?õê?îº?ä§/?ä§Ïª§Ìä∏','50');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'022','ETC','50');


-- Shoes&Bag&Acc, 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'023','?ã†Î∞?','60');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'024','Í∞?Î∞?','60');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'025','?ù¥?Ñà?õ®?ñ¥','60');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'026','Î™®Ïûê','60');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'027','Î®∏Ìîå?ü¨','60');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'028','Ï•¨ÏñºÎ¶?/?ãúÍ≥?','60');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'029','Í∏∞Ì?','60');


-- Shoes&Bag&Acc, 2nd category Ï∂îÍ?
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'030','?†àÍπÖÏä§','70');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'031','?ñëÎß?','70');
insert into spm_category_2nd(cg_2nd_idx,cg_2nd_code,cg_2nd_name,fk_cg_1st_code)
values(spm_cg_2nd_idx_seq.nextval,'032','?ä§???Çπ','70');
commit;
alter table spm_product modify pspec varchar(10) null;

select cg_1st_idx, cg_1st_code,cg_1st_name,cg_2nd_idx,cg_2nd_code,cg_2nd_name
from spm_category_1st A join spm_category_2nd B on A.cg_1st_code = B.fk_cg_1st_code

select * from spm_category_2nd where fk_cg_1st_code= '10'


String sql = "insert into spm_member(meber_idx,userid,pwd,username,post1,post2,addr1,addr2,email,hp1,hp2,hp3,gender,birthday,point,lastLoginDate,lastPwdChangeDate,status) \n"+
"values(SPM_MEMBER_IDX_SEQ.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,default,default,default,default) ";
delete spm_member where member_idx in(4,5,6);
commit;
select*from spm_member;
select * from spm_product_image;
desc spm_member;
desc spm_memo;
select*from spm_product;
alter table spm_product modify pcontent varchar(400);
update spm_member set status=7 where userid='admin';
select*from spm_product_idx_seq;
commit;

create table spm_product_image
(image_idx number not null Primary Key
, fk_pcode varchar2(8) not null
, image varchar2(100) not null
, constraint spm_product_image_pcode_fk foreign key (fk_pcode) references spm_product(pcode)
);
alter table spm_product_image drop constraint spm_product_image_pcode_fk;
alter table spm_product_image add constraint spm_product_image_pcode_fk foreign key (fk_pcode) references spm_product(pcode) on delete cascade;
commit;
select*from spm_member;
create sequence spm_product_image_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

desc spm_import;

delete spm_product;
commit;
select * from USER_CONSTRAINTS where table_name = 'SPM_IMPORT';

delete spm_product where pcode='4001314';
commit;
select*from spm_stock order by stock_idx desc;
select*from spm_product_image;
select*from spm_import;
desc spm_import;
select*from spm_product_image;
select*from spm_product;

select * from spm_stock where psize= '';
insert into spm_stock(stock_idx,fk_pcode,psize,color,pqty) values(18,'2000517',null,null,10);

 select stock_idx from spm_stock 
 where fk_pcode = '3001012' and  psize = '' and color = 'black' 
 select*from jsp_cart;
 -------------------
create table spm_cart
(cart_idx number Primary Key
,fk_userid varchar2(40) not null references spm_member(userid)
,fk_pcode varchar2(8) not null references spm_product(pcode)
,fk_stock_idx number not null references spm_stock(stock_idx)
,oqty number not null
);
 -------------------
 create sequence spm_cart_idx_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-------------------------
 create sequence spm_like_seq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
 select distinct color from spm_stock where fk_pcode='100021'
 
 desc spm_cart;
 
select*from spm_product;

delete spm_product;

commit;
select*from spm_stock;
desc spm_order;
select spm_order_detail_idx_seq.nextval from dual;
alter table spm_product add pcontent varchar2(400);
insert into spm_order_detail(order_detail_idx,fk_odrcode,fk_pcode,fk_stock_idx,oqty,totalprice,dvrdate,dvrstatus) values(order_detail_idx_seq.nextval,,'',?,?.?,default,default)
select*from spm_order;
alter table spm_order add dvradress varchar2(200)
alter table spm_order add receiver varchar2(40)

select*from spm_order_detail;

insert into spm_order_detail(order_detail_idx,fk_odrcode,fk_pcode,fk_stock_idx,oqty,totalprice,dvrdate,dvrstatus) values(spm_order_detail_idx_seq.nextval,'S181210-39','4001336',36,1,22222,sysdate+3,default)
select*from spm_product;

create table spm_like
(like_idx number not null primary key
,fk_userid varchar2(40) not null
,fk_pcode varchar2(8) not null
)


delete spm_order_detail;
rollback;
select*from spm_stock;
commit;
delete from spm_order where dvrfee=0;
select

alter table spm_order add receivername varchar2(20) not null; 
alter table spm_order add receiverpost1 number(3) not null;
alter table spm_order add receiverpost2 number(3) not null;
alter table spm_order add receiveraddr1 varchar2(50) not null;
alter table spm_order add receiveraddr2 varchar2(50) not null;
alter table spm_order add receiveremail varchar2(200) not null;
alter table spm_order add receiverhp1 varchar2(3) not null;
alter table spm_order add receiverhp2 varchar2(50) not null;
alter table spm_order add receiverhp3 varchar2(50) not null;

delete spm_order;
delete spm_order_detail;

select*from spm_order;
select*from spm_order_detail;