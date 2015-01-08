PRAGMA foreign_keys = ON
/
CREATE TABLE training_category
(
	training_category_id integer PRIMARY KEY AUTOINCREMENT,
	training_category_name text
)
/
CREATE TABLE training_menu
(
	training_menu_id integer PRIMARY KEY,
	training_name text,
	mets real,
	training_category_id integer,
	color text,
	FOREIGN KEY(training_category_id) REFERENCES training_category(training_category_id)
)
/
CREATE TABLE user
(
	user_id text PRIMARY KEY,
	user_name text,
	password text,
	gender text,
	height integer,
	weight integer,
	age integer,
	quiet_heart_rate,
	mail text
)
/
CREATE TABLE training
(
	id integer PRIMARY KEY AUTOINCREMENT,
	training_category_id integer,
	user_id integer,
	start_date text,
	start_time text,
	play_time text,
	target_heart_rate integer,
	target_cal integer,
	consumption_cal integer,
	heart_rate_avg integer,
	strange integer,
	distance real,
	FOREIGN KEY(user_id) REFERENCES user(user_id),
	FOREIGN KEY(training_category_id) REFERENCES training_category(training_category_id)
)
/
CREATE TABLE heart_rate_log
(
	log_id integer PRIMARY KEY AUTOINCREMENT,
	training_id integer,
	user_id integer, 
	FOREIGN KEY(user_id) REFERENCES training(user_id),
	FOREIGN KEY(training_id) REFERENCES training_menu(training_id)
)
/
CREATE TABLE heart_rate
(
	heart_rate_id integer PRIMARY KEY AUTOINCREMENT,
	training_id integer,
	time text,
	heart_rate integer,
	FOREIGN KEY(training_id) REFERENCES training(training_id)
)
/
CREATE TABLE training_log
(
	log_id integer PRIMARY KEY AUTOINCREMENT,
	id integer,
	heart_rate integer,
	dis_x real,
	dis_y real,
	time text,
	lap text,
	split text,
	target_heart_rate integer,
	FOREIGN KEY(id) REFERENCES training(id)
)
/
CREATE TABLE training_rest_log
(
	log_id integer PRIMARY KEY AUTOINCREMENT,
	training_id integer,
	rest text,
	FOREIGN KEY(training_id) REFERENCES training(training_id)
)
/
CREATE TABLE training_play
(
	id integer,
	training_menu_id integer,
	training_time integer,
	FOREIGN KEY(id) REFERENCES training(id),
	FOREIGN KEY(training_menu_id) REFERENCES training_menu(training_menu_id)
)
/
CREATE TABLE permission
(          
	permission_id integer PRIMARY KEY,
	name text,
	compel_withdrawal integer,
	dissolution integer,
	permission_change integer,
	group_info_change integer,
	member_add_manage integer,
	member_data_check integer,
	member_list_view integer,
	group_info_view integer,
	withdrawal integer,
	join_status integer,
	group_news integer
)
/