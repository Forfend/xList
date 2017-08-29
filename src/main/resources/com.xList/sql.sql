create table notes
(
	id int auto_increment
		primary key,
	note text null,
	note_title varchar(255) null,
	is_archieve tinyint(1) not null,
	date_added varchar(10) not null,
	color varchar(6) default 'FFFFFF' null,
	user_id int not null
)
;

create index note_user_fk
	on notes (user_id)
;

create table shared_notes
(
	id int auto_increment
		primary key,
	user_id int not null,
	notes_id int not null,
	constraint shared_notes_notes_id_fk
		foreign key (notes_id) references notes (id)
			on update cascade on delete cascade
)
;

create index shared_notes_notes_id_fk
	on shared_notes (notes_id)
;

create index shared_notes_user_id_fk
	on shared_notes (user_id)
;

create table user
(
	id int auto_increment
		primary key,
	username varchar(100) not null,
	password varchar(20) not null,
	name varchar(30) null,
	constraint user_username_uindex
		unique (username)
)
;

alter table notes
	add constraint note_user_fk
		foreign key (user_id) references user (id)
			on update cascade on delete cascade
;

alter table shared_notes
	add constraint shared_notes_user_id_fk
		foreign key (user_id) references user (id)
			on update cascade on delete cascade
;

create table categories
(
	id int auto_increment
		primary key,
	user_id int null,
	category_name varchar(36) null,
	note_id int null,
	constraint categories_unique_category_for_user
		unique (category_name, user_id)
)
;
