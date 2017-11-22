/* This SQL script is used to create the DB and Relations for the AKO LMS */

CREATE DATABASE IF NOT EXISTS ako;

/* User Types */
CREATE TABLE IF NOT EXISTS ako.UserType (
	id INT(2) NOT NULL PRIMARY KEY,
	type VARCHAR(16) NOT NULL
);

/* Populate the UserType relation */
INSERT INTO ako.UserType VALUES ('01','Student');
INSERT INTO ako.UserType VALUES ('02','Teacher');
INSERT INTO ako.UserType VALUES ('03','Admin');
INSERT INTO ako.UserType VALUES ('04','TeacherAssistant');
INSERT INTO ako.UserType VALUES ('05','Grader');

/* User Relation */
CREATE TABLE IF NOT EXISTS ako.User (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	lastModifyDate TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
	firstName VARCHAR(30) NOT NULL,
	middleName VARCHAR(30) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	birthDate DATE NOT NULL,
	email VARCHAR(70) NOT NULL,
	password VARCHAR(70) NOT NULL,
	userTypeId INT(2) NOT NULL,
	hasMfaActive TINYINT(1) NOT NULL,
	secret VARCHAR(70) NOT NULL,
	userName VARCHAR(70) NOT NULL,
	CONSTRAINT FOREIGN KEY (userTypeId) REFERENCES ako.UserType(id)
);
/* Starting value for the User ID */
ALTER TABLE ako.User AUTO_INCREMENT = 1000000001;

/* DB needs for the auto increment to be a primary key. Add a unique key for a User */
ALTER TABLE ako.User ADD UNIQUE KEY (firstName,middleName,lastName,birthDate,email,userTypeId);

/* Populating our User table since we do not have the Admin feature as a requirement. Passwords to be defined later */
INSERT INTO ako.User (firstName,lastName,middleName,birthDate,email,password,userTypeId,hasMfaActive,secret,userName) 
		VALUES ('Noel','Buruca','A','2017-09-01','nburuca@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','01','1','3E6ENQIGPIDDPFAI','nburuca@terpmail.umd.edu');
INSERT INTO ako.User (firstName,lastName,middleName,birthDate,email,password,userTypeId,hasMfaActive,secret,userName) 
		VALUES ('Renuka','Dalal','A','2017-09-01','rdalal@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','02','1','LGYN4HASTVHDC5YQ','rdalal@terpmail.umd.edu');
INSERT INTO ako.User (firstName,lastName,middleName,birthDate,email,password,userTypeId,hasMfaActive,secret,userName)  
		VALUES ('Tim','Phillips','A','2017-09-01','tphillips@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','01','0','JC5XKGIUQVHX7SIJ','tphillips@terpmail.umd.edu');
INSERT INTO ako.User (firstName,lastName,middleName,birthDate,email,password,userTypeId,hasMfaActive,secret,userName) 
		VALUES ('Vishakha','Sadhwani','A','2017-09-01','vsadhwani@terpmail.umd.edu','$2a$10$qTS/we2W1687My6zAeuUd.yGDjEc8npQwbVtogFgzZV53DCveG6Ue','01','0','DSLWOBT56WMONKPT','vsadhwani@terpmail.umd.edu');
INSERT INTO ako.User (firstName,lastName,middleName,birthDate,email,password,userTypeId,hasMfaActive,secret,userName) 
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
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	shortName VARCHAR(7) NOT NULL, /* I.E. ENPM613 */
	year VARCHAR(4) NOT NULL,
	semesterId SMALLINT(2) NOT NULL,
	section VARCHAR(4) NOT NULL, 
	description TEXT NOT NULL,
	CONSTRAINT FOREIGN KEY (semesterId) REFERENCES ako.Semester(id)
);

/* DB needs for the auto increment to be a primary key. Add a unique key for the course info */
ALTER TABLE ako.Course ADD UNIQUE KEY (shortName,year,semesterId,section);

/* Populating our Course table since we do not have the Admin feature as a requirement*/
INSERT INTO ako.Course (shortName,year,semesterId,section,description) 
		VALUES ('ENPM611','2017','01','0101','Software Engineering');
INSERT INTO ako.Course (shortName,year,semesterId,section,description) 
		VALUES ('ENPM612','2017','01','0101','Software Requirements');
INSERT INTO ako.Course (shortName,year,semesterId,section,description) 
		VALUES ('ENPM613','2017','01','0101','Software Design');
INSERT INTO ako.Course (shortName,year,semesterId,section,description) 
		VALUES ('ENPM614','2017','01','0101','Software Testing');

/* Modules Relation */
CREATE TABLE IF NOT EXISTS ako.Modules (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	courseId INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	description TEXT,
	CONSTRAINT FOREIGN KEY (courseId) REFERENCES ako.Course(id) ON DELETE CASCADE
);

/* Files Relation */ 
/* May Not Be Needed and replaced by the AWS S3  */
CREATE TABLE IF NOT EXISTS ako.File (
	id MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	moduleId INT, 
	name VARCHAR(50) NOT NULL,
	description TEXT NOT NULL,
	fileS3Url VARCHAR(100) NOT NULL 
);

/* Syllabus Relation */
CREATE TABLE IF NOT EXISTS ako.Syllabus (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	courseId INT NOT NULL,
	fileId MEDIUMINT, /* Could be NULL */
	name VARCHAR(50) NOT NULL,
	description TEXT NOT NULL,
	syllabusText TEXT NOT NULL,
	CONSTRAINT FOREIGN KEY (courseId) REFERENCES ako.Course(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (fileId) REFERENCES ako.File(id) 
);

/* Message Relation */
CREATE TABLE IF NOT EXISTS ako.Message (
	id MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	courseId INT NOT NULL,
	subject VARCHAR(100) NOT NULL,
	body TEXT NOT NULL,
	previousMessageId MEDIUMINT, /* Could be Null */
	CONSTRAINT FOREIGN KEY (courseId) REFERENCES ako.Course(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (previousMessageId) REFERENCES ako.Message(id)
);

/* Group Relation */ 
CREATE TABLE IF NOT EXISTS ako.Group (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	createDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	courseId INT NOT NULL,
	name VARCHAR(100) NOT NULL,
	description TEXT NOT NULL,
	CONSTRAINT FOREIGN KEY (courseId) REFERENCES ako.Course(id) ON DELETE CASCADE
);

/***************************** Relationships *****************************/

/* Course User. Course has many Users, User has many Courses */
CREATE TABLE IF NOT EXISTS ako.CourseUser (
	courseId INT NOT NULL,
	userId INT NOT NULL,
	CONSTRAINT FOREIGN KEY (courseId) REFERENCES ako.Course(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (userId) REFERENCES ako.User(id) ON DELETE CASCADE,
	PRIMARY KEY (courseId, userId)
);

/* Populating our CourseUser table since we do not have the Admin feature as a requirement.*/
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM611'),(select id from ako.User where firstName = 'Renuka'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM611'),(select id from ako.User where firstName = 'Noel'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM611'),(select id from ako.User where firstName = 'Tim'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM611'),(select id from ako.User where firstName = 'Vishakha'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM613'),(select id from ako.User where firstName = 'Prashant'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM613'),(select id from ako.User where firstName = 'Noel'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM613'),(select id from ako.User where firstName = 'Tim'));
INSERT INTO ako.CourseUser VALUES ((select id from ako.Course where shortName = 'ENPM613'),(select id from ako.User where firstName = 'Vishakha'));

/* Group User. Group has many Users, User has many Groups */
CREATE TABLE IF NOT EXISTS ako.GroupUser (
	groupId INT NOT NULL,
	userId INT NOT NULL,
	CONSTRAINT FOREIGN KEY (groupId) REFERENCES ako.Group(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (userId) REFERENCES ako.User(id) ON DELETE CASCADE,
	PRIMARY KEY (groupId, userId)
);

/* Message Files. Message has many Files, File Has Many Messages */ 
/* May Not Be Needed and replaced by the AWS S3  */
CREATE TABLE IF NOT EXISTS ako.MessageFile (	
	messageId MEDIUMINT NOT NULL,
	fileId MEDIUMINT NOT NULL,
	CONSTRAINT FOREIGN KEY (messageId) REFERENCES ako.Message(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (fileId) REFERENCES ako.File(id) ON DELETE CASCADE,
	PRIMARY KEY (messageId, fileId)
);

/* The folowing may not be needed if we use the toRecipients, ccRecipients, and bccRecipients attributes in the Message relation */
/* Message User Type. Maybe be From, To, Cc, or Bcc */
CREATE TABLE IF NOT EXISTS ako.MessageUserType (
	id SMALLINT(2) NOT NULL PRIMARY KEY,
	type VARCHAR(4) NOT NULL
);

INSERT INTO ako.MessageUserType VALUES ('01','From');
INSERT INTO ako.MessageUserType VALUES ('02','To');
INSERT INTO ako.MessageUserType VALUES ('03','Cc');
INSERT INTO ako.MessageUserType VALUES ('04','Bcc');

CREATE TABLE IF NOT EXISTS ako.MessageUser (
	messageId MEDIUMINT NOT NULL,
	userId INT NOT NULL, /* Could be an individual user */
	groupId INT, /* Could be an individual group. For each member of the group, insert a record into this relation. */
	messageUserTypeId SMALLINT NOT NULL,
	CONSTRAINT FOREIGN KEY (messageId) REFERENCES ako.Message(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (userId) REFERENCES ako.User(id),
	CONSTRAINT FOREIGN KEY (groupId) REFERENCES ako.Group(id),
	CONSTRAINT FOREIGN KEY (messageUserTypeId) REFERENCES ako.MessageUserType(id),
	PRIMARY KEY (messageId, userId, messageUserTypeId)
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





