Homework number 5: hibernate/JPA queries

By Oleksiy Grechnyev

Requires the database with Office, Customer etc. from the Weiberg, Groff, Oppel SQL book.
(I created it before in 2 previous homeworks, sorry I didn't repeat it here).

You must specify connection parameters in hubernate.cfg.xml.

Each test is done with both HQL/JPQL and (the modern) criteria API. I also used static model classes for criteria.

For simplicity, I used Object[] rather than tuples for criteria tests, in real life it's better to use tuples.

I also wrote a Physical Naming Strategy which translates camel case into underscores, something which as far as I know is missing in modern hibernate.