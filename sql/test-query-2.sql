select * from translations where resource_id=282;

select * from cities where name_resource_id=3;

SELECT locale_id, translation FROM translations WHERE resource_id=3;

SELECT * FROM translations;

select * from resources;

delete from cities where id=1;

select * from cities;

select * from regions;

delete from regions where id=1;

delete from resources where id =3;

select id, region, name_resource_id, longitude, latitude, translation from cities 
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id
where locale_id=2;

select cities.id, region, name_resource_id, longitude, latitude, lang, country, translation from cities 
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id
join locales on locales.id=locale_id
ORDER BY cities.id;

select regions.id, translation, country from regions
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id;


select distinct cities.id from cities;

select users.id, login, pass, role_name from users
join roles on role=roles.id;