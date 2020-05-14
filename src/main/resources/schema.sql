
-- jdbc:h2:mem:products is active

--create schema

create table review
(
  id int auto_increment,
  user_name varchar(255) not null,
  product_id int not null,

  title varchar(500) not null comment 'review title',
  rating int null comment 'rating of review',

  is_verified_purchase boolean,
  is_helpful boolean,
  is_abuse boolean,
  description varchar(5000) not null,
  constraint reviews_id_uindex unique (id)
);


alter table review add primary key (id);



INSERT INTO REVIEW (user_name,product_id,title,rating,is_verified_purchase,is_helpful,is_abuse,description) 
VALUES ('viladamir34',8,'vitae mauris sit',3,'FALSE','TRUE','FALSE','varius ultrices, mauris ipsum'),
       ('abuzer',7,'metus urna',2,'TRUE','TRUE','TRUE','odio. Aliquam vulputate ullamcorper magna. Sed eu'),
       ('viladamir34',9,'amet ornare lectus justo',4,'FALSE','FALSE','FALSE','cursus, diam at pretium aliquet, metus urna'),
       ('tansudasli',1,'justo',2,'TRUE','FALSE','TRUE','ultrices posuere cubilia Curae; Phasellus ornare. Fusce mollis.'),
       ('tansudasli',6,'massa. Quisque porttitor',1,'FALSE','FALSE','TRUE','augue malesuada malesuada. Integer'),
       ('viladamir34',7,'Quisque varius. Nam porttitor',5,'TRUE','TRUE','TRUE','diam luctus lobortis. Class aptent taciti'),
       ('tansudasli',5,'amet orci. Ut sagittis',5,'FALSE','TRUE','FALSE','nunc sed libero. Proin sed turpis nec'),
       ('abuzer',3,'lorem semper auctor. Mauris vel',4,'TRUE','TRUE','TRUE','odio. Aliquam vulputate ullamcorper magna. Sed'),
       ('tansudasli',3,'ante. Nunc mauris',4,'TRUE','TRUE','TRUE','Fusce diam nunc, ullamcorper eu,'),
       ('viladamir34',7,'sem semper erat,',4,'FALSE','FALSE','FALSE','vel est tempor bibendum. Donec'),
       ('tansudasli',3,'porta elit,',2,'FALSE','TRUE','TRUE','Aliquam tincidunt, nunc ac'),
       ('tansudasli',6,'et malesuada fames ac turpis',2,'FALSE','TRUE','TRUE','velit eget laoreet posuere, enim nisl elementum purus,'),
       ('abuzer',1,'felis, adipiscing fringilla, porttitor vulputate,',3,'TRUE','FALSE','FALSE','Donec luctus aliquet odio. Etiam'),
       ('tansudasli',2,'ullamcorper magna. Sed',3,'FALSE','TRUE','FALSE','scelerisque dui. Suspendisse ac metus vitae'),
       ('tansudasli',10,'consectetuer adipiscing elit. Aliquam',2,'FALSE','FALSE','TRUE','quis lectus. Nullam suscipit, est'),
       ('tansudasli',9,'a,',1,'TRUE','TRUE','FALSE','Fusce feugiat. Lorem ipsum dolor'),
       ('viladamir34',10,'scelerisque neque sed sem egestas',4,'FALSE','FALSE','FALSE','felis eget varius ultrices, mauris ipsum porta'),
       ('abuzer',3,'molestie orci tincidunt',2,'FALSE','FALSE','TRUE','neque sed sem egestas blandit.'),
       ('abuzer',8,'lorem, auctor',4,'TRUE','TRUE','TRUE','est, mollis non, cursus non, egestas a,'),
       ('tansudasli',6,'eros nec',4,'TRUE','FALSE','FALSE','ridiculus mus. Proin vel nisl.'),
       ('tansudasli',3,'augue ut lacus.',4,'FALSE','FALSE','TRUE','Donec non justo. Proin non massa non ante'),
       ('abuzer',10,'quam a felis ullamcorper',3,'FALSE','FALSE','FALSE','aliquet lobortis, nisi nibh lacinia orci, consectetuer'),
       ('tansudasli',8,'non nisi. Aenean',3,'FALSE','FALSE','FALSE','libero nec ligula consectetuer'),
       ('abuzer',2,'dolor',3,'TRUE','FALSE','TRUE','nibh enim, gravida sit amet, dapibus id, blandit'),
       ('tansudasli',1,'mi pede,',4,'TRUE','FALSE','FALSE','dictum augue malesuada'),
       ('viladamir34',6,'at, velit. Pellentesque ultricies',5,'TRUE','FALSE','TRUE','Phasellus dolor elit, pellentesque a,'),('viladamir34',4,'Phasellus ornare.',1,'TRUE','FALSE','TRUE','amet, dapibus id, blandit at,'),('viladamir34',2,'vulputate, risus a',1,'FALSE','TRUE','TRUE','rutrum urna, nec luctus felis'),('viladamir34',6,'non, cursus',1,'TRUE','FALSE','TRUE','orci sem eget massa. Suspendisse eleifend. Cras'),('abuzer',4,'Proin mi. Aliquam gravida mauris',2,'TRUE','TRUE','FALSE','vel nisl. Quisque'),('abuzer',3,'nonummy ut, molestie',4,'FALSE','TRUE','FALSE','placerat eget, venenatis a,'),('viladamir34',8,'mi eleifend egestas. Sed',3,'TRUE','FALSE','TRUE','vel pede blandit congue. In'),('tansudasli',1,'dictum augue malesuada malesuada.',2,'TRUE','TRUE','TRUE','ultrices, mauris ipsum'),('viladamir34',10,'vestibulum. Mauris magna. Duis',1,'FALSE','FALSE','FALSE','sagittis placerat. Cras dictum ultricies'),('tansudasli',7,'adipiscing, enim mi',5,'TRUE','FALSE','FALSE','magna, malesuada vel, convallis in, cursus'),('abuzer',10,'justo. Proin non massa non',1,'FALSE','FALSE','TRUE','tristique aliquet. Phasellus fermentum convallis ligula.'),('viladamir34',9,'Duis cursus,',1,'TRUE','TRUE','FALSE','mattis. Cras eget nisi dictum augue'),('viladamir34',3,'fringilla purus mauris',3,'TRUE','FALSE','TRUE','cursus. Nunc mauris elit,'),('viladamir34',9,'a, facilisis non,',4,'FALSE','FALSE','FALSE','blandit enim consequat purus. Maecenas libero'),('tansudasli',5,'quis accumsan convallis,',5,'TRUE','TRUE','FALSE','nec tempus scelerisque, lorem ipsum sodales purus,'),('viladamir34',4,'risus.',2,'TRUE','FALSE','FALSE','dis parturient montes,'),('tansudasli',7,'vulputate mauris sagittis placerat.',2,'TRUE','FALSE','FALSE','mauris erat eget ipsum. Suspendisse sagittis. Nullam vitae'),('tansudasli',9,'mi eleifend egestas. Sed pharetra,',5,'TRUE','TRUE','FALSE','Vivamus rhoncus. Donec est. Nunc ullamcorper, velit'),('viladamir34',7,'urna suscipit nonummy. Fusce',4,'TRUE','TRUE','TRUE','Suspendisse sagittis. Nullam'),('viladamir34',6,'massa. Integer vitae nibh.',4,'FALSE','FALSE','FALSE','dolor. Donec fringilla. Donec feugiat metus sit amet'),('viladamir34',9,'nisl. Quisque fringilla euismod enim.',5,'FALSE','FALSE','TRUE','Vivamus euismod urna. Nullam lobortis quam a felis'),('viladamir34',3,'iaculis',3,'FALSE','TRUE','TRUE','est, congue a,'),('abuzer',7,'sollicitudin a,',2,'FALSE','TRUE','TRUE','fermentum fermentum arcu. Vestibulum ante'),('abuzer',2,'cubilia Curae; Donec',2,'TRUE','TRUE','TRUE','enim. Curabitur massa. Vestibulum'),('abuzer',10,'penatibus et magnis dis parturient',3,'FALSE','FALSE','FALSE','Etiam laoreet, libero et tristique pellentesque,'),('abuzer',1,'posuere at, velit.',3,'FALSE','TRUE','FALSE','montes, nascetur ridiculus mus. Proin'),('abuzer',8,'Nullam nisl. Maecenas malesuada',5,'TRUE','TRUE','FALSE','orci tincidunt adipiscing. Mauris molestie pharetra nibh. Aliquam'),('abuzer',6,'senectus et netus et malesuada',1,'TRUE','FALSE','FALSE','et risus. Quisque libero lacus, varius et, euismod'),('tansudasli',3,'blandit enim',5,'TRUE','FALSE','FALSE','Suspendisse non leo.'),('viladamir34',10,'cursus et, eros. Proin',3,'FALSE','FALSE','TRUE','cursus et, magna. Praesent'),('abuzer',7,'libero. Integer in magna.',4,'FALSE','TRUE','TRUE','porta elit, a feugiat tellus lorem eu'),('abuzer',2,'tortor.',5,'FALSE','TRUE','TRUE','iaculis nec, eleifend non,'),('tansudasli',10,'ut eros',4,'TRUE','TRUE','FALSE','senectus et netus et malesuada fames'),('tansudasli',1,'fringilla, porttitor vulputate,',5,'TRUE','FALSE','TRUE','Nullam vitae diam.'),('abuzer',5,'erat vel',5,'TRUE','TRUE','FALSE','ultricies dignissim lacus. Aliquam rutrum'),('tansudasli',6,'mauris sagittis',1,'FALSE','FALSE','TRUE','purus sapien, gravida non, sollicitudin'),('abuzer',3,'rutrum magna.',2,'FALSE','FALSE','FALSE','odio a purus. Duis elementum, dui'),('abuzer',3,'et ipsum',1,'TRUE','TRUE','FALSE','Donec tincidunt. Donec vitae erat vel pede blandit'),('viladamir34',10,'Aliquam adipiscing lobortis risus.',1,'FALSE','FALSE','FALSE','parturient montes, nascetur ridiculus mus. Aenean eget'),('abuzer',6,'Mauris quis turpis vitae purus',5,'FALSE','FALSE','TRUE','vestibulum massa rutrum'),('viladamir34',4,'porttitor eros nec tellus.',5,'TRUE','TRUE','FALSE','sit amet, dapibus id, blandit at, nisi. Cum'),('viladamir34',4,'consectetuer',2,'FALSE','FALSE','FALSE','felis ullamcorper viverra. Maecenas'),('abuzer',8,'fringilla est. Mauris eu turpis.',5,'FALSE','TRUE','FALSE','ut cursus luctus, ipsum leo elementum sem, vitae'),('tansudasli',2,'vel lectus.',3,'FALSE','TRUE','FALSE','Ut tincidunt orci quis'),('abuzer',1,'lacinia',1,'TRUE','FALSE','FALSE','elit, pharetra ut, pharetra sed,'),('tansudasli',1,'eget',1,'TRUE','FALSE','TRUE','Nullam ut nisi a odio'),('viladamir34',5,'purus',2,'TRUE','FALSE','TRUE','in, cursus et, eros. Proin ultrices.'),('viladamir34',3,'Sed eu',3,'TRUE','TRUE','TRUE','Nullam enim. Sed nulla ante, iaculis'),('abuzer',10,'Etiam bibendum fermentum',4,'TRUE','TRUE','FALSE','ornare, facilisis eget, ipsum.'),('viladamir34',5,'metus vitae',5,'FALSE','FALSE','TRUE','aptent taciti sociosqu ad litora torquent per'),('tansudasli',5,'et ultrices posuere',4,'TRUE','TRUE','FALSE','Integer eu lacus. Quisque imperdiet, erat'),('viladamir34',8,'amet',2,'TRUE','FALSE','TRUE','eleifend vitae, erat.'),('abuzer',6,'penatibus',2,'FALSE','FALSE','FALSE','adipiscing lacus. Ut'),('viladamir34',10,'volutpat.',5,'TRUE','FALSE','TRUE','lectus rutrum urna, nec'),('abuzer',1,'cursus, diam at pretium aliquet,',3,'TRUE','FALSE','FALSE','primis in faucibus'),('abuzer',7,'nisi.',2,'TRUE','TRUE','TRUE','elit. Etiam laoreet, libero et tristique pellentesque,'),('abuzer',8,'non leo. Vivamus nibh dolor,',3,'FALSE','TRUE','TRUE','turpis. Aliquam adipiscing lobortis risus.'),('tansudasli',2,'nisi',3,'TRUE','FALSE','TRUE','molestie orci tincidunt adipiscing. Mauris molestie pharetra nibh.'),('abuzer',10,'auctor quis, tristique ac, eleifend',2,'TRUE','FALSE','FALSE','vulputate mauris sagittis placerat.'),('abuzer',3,'porttitor tellus',5,'FALSE','FALSE','TRUE','nec mauris blandit mattis.'),('abuzer',1,'Duis a',3,'TRUE','TRUE','FALSE','enim consequat purus. Maecenas libero est, congue'),('viladamir34',4,'malesuada vel,',2,'FALSE','TRUE','FALSE','ornare. Fusce mollis. Duis'),('tansudasli',6,'In faucibus.',3,'TRUE','FALSE','FALSE','mauris, aliquam eu, accumsan sed, facilisis vitae,'),('tansudasli',6,'non, lacinia',4,'TRUE','TRUE','TRUE','mollis. Duis sit amet'),('abuzer',10,'tempor lorem, eget',2,'FALSE','TRUE','TRUE','egestas a, scelerisque'),('viladamir34',3,'Duis elementum, dui quis',5,'FALSE','FALSE','FALSE','netus et malesuada fames ac turpis egestas.'),('tansudasli',4,'nulla vulputate dui,',1,'FALSE','TRUE','TRUE','id, ante. Nunc mauris sapien, cursus in,'),('viladamir34',2,'ut, molestie',2,'TRUE','FALSE','FALSE','semper, dui lectus rutrum urna, nec luctus'),('viladamir34',8,'nulla ante,',1,'TRUE','TRUE','TRUE','a mi fringilla mi lacinia mattis.'),('viladamir34',4,'Phasellus vitae mauris sit amet',3,'FALSE','FALSE','FALSE','a, enim. Suspendisse aliquet, sem ut cursus luctus,'),('abuzer',8,'massa. Vestibulum',5,'TRUE','TRUE','TRUE','sit amet lorem semper auctor.'),('tansudasli',1,'velit. Aliquam nisl. Nulla eu',1,'FALSE','FALSE','TRUE','et tristique pellentesque, tellus sem mollis dui, in'),('viladamir34',1,'ac libero nec ligula',3,'TRUE','TRUE','TRUE','metus facilisis lorem tristique aliquet. Phasellus'),('tansudasli',9,'risus. In mi',3,'FALSE','TRUE','FALSE','dui, in sodales elit erat vitae risus. Duis'),('abuzer',8,'Proin',4,'TRUE','FALSE','TRUE','ac, feugiat non, lobortis quis, pede. Suspendisse dui.');





