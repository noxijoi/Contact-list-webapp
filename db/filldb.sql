USE maskaliova_contacts;

-- contacts table
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city, street, house_n, post_index)
                     VALUES ("Иванов", "Иван", "Иванович","1990.10.10","male","Беларус", "single",null,"ivanov@gmail.com","EPAM",
                             "Беларусь","Минск","Независимости","122a",220000);
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city, street, house_n, post_index)
                     VALUES ("Петров", "Петр", "Петрович","1995.2.12","male","Беларус", "married","petr.by","petrov@gmail.com","iTransiton",
                      "Беларусь","Минск","Коласа","12",220001);
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city, street, house_n, post_index)
VALUES ("Маскалева", "Мария", "Алексевна","2000.2.12","female","Беларус", "single",null,"noxijoi@gmail.com",null,
        "Беларусь","Минск","Левкова","12",2200);
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city)
VALUES ("Джон", "Уик", "Батькович","1990.3.12","male","Беларус", "single",null,"noxijoi@gmail.com","мафия",
        "США","Лас-Вегас");
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city)
VALUES ("Джон", "Уик", "Батькович","1990.3.12","male","Беларус", "single",null,"noxijoi@gmail.com","мафия",
        "США","Лас-Вегас");
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city)
VALUES ("Имя", "Фамилия", "Отчествович","1980.2.12","male","Индия", "married",null,"noxijoi@gmail.com","Apple",
        "Индия","Нью-Дели");


