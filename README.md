**Projet OpenClassrooms #6**

# Livrables:
  - Diagramme UML:

![Diagramme de classe UML](https://github.com/KvxSZ/Perchet-Kylian-PayMyBuddy/assets/145575222/e750f74b-7c9a-4c65-8bf1-2f12440a9533)

- Modèle physique des données:

![Modèle physique des données](https://github.com/KvxSZ/Perchet-Kylian-PayMyBuddy/assets/145575222/da861c71-a473-4a7e-95fb-ebd9784e7f5b)

- Test de couverture:

![Screenshot_1](https://github.com/KvxSZ/Perchet-Kylian-PayMyBuddy/assets/145575222/aaff9e1a-fd8e-4cc3-a1f6-1768c5250d61)

![image](https://github.com/KvxSZ/Perchet-Kylian-PayMyBuddy/assets/145575222/29201b97-837e-4691-b873-565227748681)

- Interface WEB

  - Login:

  ![image](https://github.com/KvxSZ/Perchet-Kylian-PayMyBuddy/assets/145575222/8c06893f-6a03-4fc5-a2eb-21bf3caab44e)

  - Transfer:
  
  ![image](https://github.com/KvxSZ/Perchet-Kylian-PayMyBuddy/assets/145575222/f35e5b47-4fc0-49ef-9f1c-d254e59fd5e6)


- Information:

  Pour lancer l'application il vous faut d'abord créer une base de données, ensuite remplir les champs du fichier `application.properties` dans le dossier **ressources**:
  Renseignez l'`URL` de la base de données, l'`usurname` de l'utilisateur avec les accès et le `mot de passe` de celui-ci

  ```
  spring.datasource.url=
  spring.datasource.username=
  spring.datasource.password=
  spring.jpa.show-sql=true
  spring.thymeleaf.prefix=classpath:/templates/
  spring.thymeleaf.suffix=.html
  spring.thymeleaf.cache=false
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
  ```

  Puis ajoutez la ligne suivante afin de créer toute les tables nécessaire automatiquement: `spring.jpa.hibernate.ddl-auto=update`
  (Vous pourrez supprimer cette ligne une fois les tables créées pour ne pas altérer votre base de données)
  
  ou sinon voici le script SQL pour la création des tables:

  ```
  create table persistent_logins (series varchar(64) not null, username varchar(64) not null, last_used datetime(6) not null, token varchar(64) not null, primary key (series)); 
  create table persistent_logins_seq (next_val bigint);
  insert into persistent_logins_seq values ( 1 );
  create table transaction (transaction_id integer not null auto_increment, amount float(53) not null, date datetime(6) not null, description varchar(255) not null, receiver_id integer, sender_id integer, primary key (transaction_id)); 
  create table user (user_id integer not null auto_increment, balance float(53), email varchar(255) not null, firstname varchar(255) not null, lastname varchar(255) not null, password varchar(255) not null, primary key (user_id));
  create table user_friends (user_user_id integer not null, friends_user_id integer not null); 
  alter table transaction add constraint FKey21a233t8tlwfsbs228q3b2u foreign key (receiver_id) references user (user_id);
  alter table transaction add constraint FKjpter5yuohdb58gyg6k5nympt foreign key (sender_id) references user (user_id);
  alter table user_friends add constraint FKl4g5s1y9obixveemgpxvc76bf foreign key (friends_user_id) references user (user_id);
  alter table user_friends add constraint FK3ig667002tcaoy5u69g6lf4cj foreign key (user_user_id) references user (user_id);
  ```
  Il ne vous reste plus cas lancez l'application et créer un compte sur la page de register !

  
