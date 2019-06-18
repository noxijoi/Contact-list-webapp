USE maskaliova_contacts;

-- contacts table
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city, street, house_n, post_index)
                     VALUES ("Иванов", "Иван", "Иванович","1990.10.10","M","Беларусь", "single",null,"ivanov@gmail.com","EPAM",
                             "Беларусь","Минск","Независимости","122a",220000);
INSERT INTO contact (f_name, l_name, p_name, b_date, sex, nationality, marital_status,
                     web_site, email, company,
                     country, city, street, house_n, post_index)
                     VALUES ("Петров", "Петр", "Петрович","1995.2.12","M","Беларусь", "divorced","petr.by","petrov@gmail.com","iTransiton",
                      "Беларусь","Минск","Коласа","12",220001);

