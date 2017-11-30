/* This SQL script is used to create the DB and Relations for the AKO LMS */

CREATE DATABASE IF NOT EXISTS ako;

/* User Types */
CREATE TABLE IF NOT EXISTS ako.User_type (
	id INT(2) NOT NULL PRIMARY KEY,
	type VARCHAR(16) NOT NULL
);

/* Populate the User_type relation */
INSERT INTO ako.User_type VALUES ('01','Student');
INSERT INTO ako.User_type VALUES ('02','Teacher');
INSERT INTO ako.User_type VALUES ('03','Admin');
INSERT INTO ako.User_type VALUES ('04','TeacherAssistant');
INSERT INTO ako.User_type VALUES ('05','Grader');

/* User Relation */
CREATE TABLE IF NOT EXISTS ako.User (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	last_modify_date TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
	first_name VARCHAR(30) NOT NULL,
	middle_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	birth_date DATE NOT NULL,
	email VARCHAR(70) NOT NULL,
	password VARCHAR(70) NOT NULL,
	user_type_id INT(2) NOT NULL,
	has_mfa_active TINYINT(1) NOT NULL,
	secret VARCHAR(70) NOT NULL,
	user_name VARCHAR(70) NOT NULL,
	CONSTRAINT FOREIGN KEY (user_type_id) REFERENCES ako.User_type(id)
);
/* Starting value for the User ID */
ALTER TABLE ako.User AUTO_INCREMENT = 1000000001;

/* DB needs for the auto increment to be a primary key. Add a unique key for a User */
ALTER TABLE ako.User ADD UNIQUE KEY (first_name,middle_name,last_name,birth_date,email,user_type_id);

/* Populating our User table since we do not have the Admin feature as a requirement. Passwords to be defined later */
INSERT INTO ako.User (first_name,last_name,middle_name,birth_date,email,password,user_type_id,has_mfa_active,secret,user_name) 
		VALUES ('Noel','Buruca','A','2017-09-01','nburuca@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','01','1','3E6ENQIGPIDDPFAI','nburuca@terpmail.umd.edu');
INSERT INTO ako.User (first_name,last_name,middle_name,birth_date,email,password,user_type_id,has_mfa_active,secret,user_name) 
		VALUES ('Renuka','Dalal','A','2017-09-01','rdalal@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','02','1','LGYN4HASTVHDC5YQ','rdalal@terpmail.umd.edu');
INSERT INTO ako.User (first_name,last_name,middle_name,birth_date,email,password,user_type_id,has_mfa_active,secret,user_name)  
		VALUES ('Tim','Phillips','A','2017-09-01','tphillips@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','01','0','JC5XKGIUQVHX7SIJ','tphillips@terpmail.umd.edu');
INSERT INTO ako.User (first_name,last_name,middle_name,birth_date,email,password,user_type_id,has_mfa_active,secret,user_name) 
		VALUES ('Vishakha','Sadhwani','A','2017-09-01','vsadhwani@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','01','0','DSLWOBT56WMONKPT','vsadhwani@terpmail.umd.edu');
INSERT INTO ako.User (first_name,last_name,middle_name,birth_date,email,password,user_type_id,has_mfa_active,secret,user_name) 
		VALUES ('Prashant','Rathod','A','2017-09-01','prathod@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','02','1','T37RBNPZMU3UCJSD','prathod@terpmail.umd.edu');

/* Semester Identifier. 2 Digit Identifier */
CREATE TABLE IF NOT EXISTS ako.Semester (
	id SMALLINT(2) NOT NULL PRIMARY KEY,
	name VARCHAR(8) NOT NULL
);

INSERT INTO ako.Semester VALUES ('01','Fall'); /* Fall */
INSERT INTO ako.Semester VALUES ('02','Winter 1'); /* Winter 1 */
INSERT INTO ako.Semester VALUES ('03','Winter 2'); /* Winter 2 */
INSERT INTO ako.Semester VALUES ('04','Spring'); /* Spring */
INSERT INTO ako.Semester VALUES ('05','Summer 1'); /* Summer 1 */
INSERT INTO ako.Semester VALUES ('06','Summer 2'); /* Summer 1 */

/* Course Relation */
CREATE TABLE IF NOT EXISTS ako.Course (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	short_name VARCHAR(7) NOT NULL, /* I.E. ENPM613 */
	year VARCHAR(4) NOT NULL,
	semester_id SMALLINT(2) NOT NULL,
	section VARCHAR(4) NOT NULL, 
	description TEXT NOT NULL,
	CONSTRAINT FOREIGN KEY (semester_id) REFERENCES ako.Semester(id)
);

/* DB needs for the auto increment to be a primary key. Add a unique key for the course info */
ALTER TABLE ako.Course ADD UNIQUE KEY (short_name,year,semester_id,section);

/* Populating our Course table since we do not have the Admin feature as a requirement*/
INSERT INTO ako.Course (short_name,year,semester_id,section,description) 
		VALUES ('ENPM611','2017','01','0101','Software Engineering');
INSERT INTO ako.Course (short_name,year,semester_id,section,description) 
		VALUES ('ENPM612','2017','01','0101','Software Requirements');
INSERT INTO ako.Course (short_name,year,semester_id,section,description) 
		VALUES ('ENPM613','2017','01','0101','Software Design');
INSERT INTO ako.Course (short_name,year,semester_id,section,description) 
		VALUES ('ENPM614','2017','01','0101','Software Testing');

/* Module Relation */
CREATE TABLE IF NOT EXISTS ako.Module (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	course_id INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	description TEXT,
	CONSTRAINT FOREIGN KEY (course_id) REFERENCES ako.Course(id) ON DELETE CASCADE
);

/* File Relation */ 
CREATE TABLE IF NOT EXISTS ako.File (
	id MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	module_id INT, 
	name VARCHAR(50) NOT NULL,
	description TEXT NOT NULL,
	file_s3_url VARCHAR(100) NOT NULL 
);

/* Syllabus Relation */
CREATE TABLE IF NOT EXISTS ako.Syllabus (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	course_id INT NOT NULL,
	file_id MEDIUMINT, /* Could be NULL */
	name VARCHAR(50) NOT NULL,
	description TEXT NOT NULL,
	syllabus_text TEXT NOT NULL,
	CONSTRAINT FOREIGN KEY (course_id) REFERENCES ako.Course(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (file_id) REFERENCES ako.File(id) 
);

/* Message Relation */
CREATE TABLE IF NOT EXISTS ako.Message (
	id MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	course_id INT NOT NULL,
	subject VARCHAR(100) NOT NULL,
	body TEXT NOT NULL,
	previous_message_id MEDIUMINT, /* Could be Null */
	CONSTRAINT FOREIGN KEY (course_id) REFERENCES ako.Course(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (previous_message_id) REFERENCES ako.Message(id)
);

/* Group Relation */ 
CREATE TABLE IF NOT EXISTS ako.Group (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	course_id INT NOT NULL,
	name VARCHAR(100) NOT NULL,
	description TEXT NOT NULL,
	CONSTRAINT FOREIGN KEY (course_id) REFERENCES ako.Course(id) ON DELETE CASCADE
);

/***************************** Relationships *****************************/

/* Course User. Course has many Users, User has many Courses */
CREATE TABLE IF NOT EXISTS ako.Course_user (
	course_id INT NOT NULL,
	user_id INT NOT NULL,
	CONSTRAINT FOREIGN KEY (course_id) REFERENCES ako.Course(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (user_id) REFERENCES ako.User(id) ON DELETE CASCADE,
	PRIMARY KEY (course_id, user_id)
);

/* Populating our Course_user table since we do not have the Admin feature as a requirement.*/
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM611'),(select id from ako.User where first_name = 'Renuka'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM611'),(select id from ako.User where first_name = 'Noel'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM611'),(select id from ako.User where first_name = 'Tim'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM611'),(select id from ako.User where first_name = 'Vishakha'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM613'),(select id from ako.User where first_name = 'Prashant'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM613'),(select id from ako.User where first_name = 'Noel'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM613'),(select id from ako.User where first_name = 'Tim'));
INSERT INTO ako.Course_user VALUES ((select id from ako.Course where short_name = 'ENPM613'),(select id from ako.User where first_name = 'Vishakha'));

/* Group User. Group has many Users, User has many Groups */
CREATE TABLE IF NOT EXISTS ako.Group_user (
	group_id INT NOT NULL,
	user_id INT NOT NULL,
	CONSTRAINT FOREIGN KEY (group_id) REFERENCES ako.Group(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (user_id) REFERENCES ako.User(id) ON DELETE CASCADE,
	PRIMARY KEY (group_id, user_id)
);

/* Message Files. Message has many Files, File Has Many Messages */ 
/* May Not Be Needed and replaced by the AWS S3  */
CREATE TABLE IF NOT EXISTS ako.Message_file (	
	message_id MEDIUMINT NOT NULL,
	file_id MEDIUMINT NOT NULL,
	CONSTRAINT FOREIGN KEY (message_id) REFERENCES ako.Message(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (file_id) REFERENCES ako.File(id) ON DELETE CASCADE,
	PRIMARY KEY (message_id, file_id)
);

/* The folowing may not be needed if we use the toRecipients, ccRecipients, and bccRecipients attributes in the Message relation */
/* Message User Type. Maybe be From, To, Cc, or Bcc */
CREATE TABLE IF NOT EXISTS ako.Message_user_type (
	id SMALLINT(2) NOT NULL PRIMARY KEY,
	type VARCHAR(4) NOT NULL
);

INSERT INTO ako.Message_user_type VALUES ('01','From');
INSERT INTO ako.Message_user_type VALUES ('02','To');
INSERT INTO ako.Message_user_type VALUES ('03','Cc');
INSERT INTO ako.Message_user_type VALUES ('04','Bcc');

CREATE TABLE IF NOT EXISTS ako.Message_user (
	message_id MEDIUMINT NOT NULL,
	user_id INT NOT NULL, /* Could be an individual user */
	group_id INT, /* Could be an individual group. For each member of the group, insert a record into this relation. */
	message_user_type_id SMALLINT NOT NULL,
	CONSTRAINT FOREIGN KEY (message_id) REFERENCES ako.Message(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (user_id) REFERENCES ako.User(id),
	CONSTRAINT FOREIGN KEY (group_id) REFERENCES ako.Group(id),
	CONSTRAINT FOREIGN KEY (message_user_type_id) REFERENCES ako.Message_user_type(id),
	PRIMARY KEY (message_id, user_id, message_user_type_id)
);

/* drop users and flush privileges; owing to this bug : https://bugs.mysql.com/bug.php?id=28331 */
drop user ako_read;
drop user ako_admin;
flush privileges;

/* Create a read only user and an admin user */
CREATE USER ako_read IDENTIFIED BY 'akoread';
GRANT SELECT ON ako.* to ako_read;

CREATE USER ako_admin IDENTIFIED BY 'Ak0Adm!N';
GRANT ALL ON ako.* to ako_admin;

/* Create a authority table to record user roles, in order to use role based authentication */
CREATE TABLE IF NOT EXISTS ako.Authority (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL
);

/* Intert default roles */
INSERT INTO ako.Authority(name) VALUES ('ROLE_USER');
INSERT INTO ako.Authority(name) VALUES ('ROLE_ADMIN');


/* Create a User_authority table to record user - authority relation*/
CREATE TABLE IF NOT EXISTS ako.User_authority (
	user_id INT NOT NULL,
	authority_id INT NOT NULL,
	CONSTRAINT FOREIGN KEY (authority_id) REFERENCES ako.Authority (id),
	CONSTRAINT FOREIGN KEY (user_id) REFERENCES ako.User (id)
);


/* Insert default values */
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000001','1');
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000002','1');
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000004','1');
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000005','1');
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000003','1');

/* make some users admin values */
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000005','2');
INSERT INTO ako.User_authority(user_id,authority_id) VALUES ('1000000003','2');





