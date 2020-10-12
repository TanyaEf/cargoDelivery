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
ORDER BY cities.id
limit 0, 400;

select cities.id, region, name_resource_id, longitude, latitude, lang, country, translation from cities 
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id
join locales on locales.id=locale_id
where region=2
ORDER BY cities.id;

SELECT COUNT(*) FROM cities;

select cities.id, region, name_resource_id, longitude, latitude, lang, country, translation from cities 
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id
join locales on locales.id=locale_id
ORDER BY cities.id;

select regions.id, regions.country, name_resource_id, lang, locales.country, translation from regions
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id
join locales on locales.id=locale_id
ORDER BY regions.id;

select regions.id, translation, country from regions
join resources on name_resource_id=resources.id
join translations on resource_id=resources.id;


select distinct cities.id from cities;

select * from users;

select users.id, login, pass, role_name from users
join roles on role=roles.id;

SELECT * FROM cargo_types
join translations on name_resource_id = resource_id;

SELECT * FROM statuses
join translations on name_resource_id = resource_id;

SELECT * FROM statuses
join translations on name_resource_id = resource_id
where name_resource_id=7;

SELECT statuses.id, translation, lang, country FROM statuses
join translations on name_resource_id = resource_id
join locales on locales.id=locale_id
order by statuses.id;

SELECT cargo_types.id, translation, lang, country FROM cargo_types
join translations on name_resource_id = resource_id
join locales on locales.id=locale_id
order by cargo_types.id;

select * from delivery_status;

select status_id, date_time from delivery_status
where delivery_id=3;

insert into deliveries (user_id, city_id, adress, cargo_type, weight, volume, cost)
values (1, 1, 'skdjfhsdfh', 1, 10, 10, 22.2);

select * from deliveries
where id=1;

select * from deliveries
join delivery_status on deliveries.id = delivery_id
where status_id=1;

select * from deliveries
join delivery_status on deliveries.id = delivery_id;

select  actualdelivery_status.delivery_id, deliveries.user_id, 
	deliveries.origin_city_id, deliveries.destination_city_id, deliveries.adress, 
    deliveries.cargo_type, deliveries.weight, 
    deliveries.volume, deliveries.cost,
	actualdelivery_status.date_time , delivery_status.status_id
from 
	(select delivery_id, max(date_time) as date_time
    from delivery_status
	group by delivery_id) as actualdelivery_status
inner join delivery_status 
	on actualdelivery_status.delivery_id = delivery_status.delivery_id
	and actualdelivery_status.date_time = delivery_status.date_time
    and delivery_status.status_id=5
inner join deliveries on deliveries.id = actualdelivery_status.delivery_id
		and CASE
			WHEN 0 > 0 THEN deliveries.origin_city_id = 1
			ELSE true
			END
		and CASE
			WHEN 0 > 0 THEN deliveries.destination_city_id = 1
			ELSE true
			END
		and CASE
			WHEN 0 > 0 THEN deliveries.user_id = 1
			ELSE true
			END
limit 0,10;


select count(*)
from 
	(select delivery_id, max(date_time) as date_time
    from delivery_status
	group by delivery_id) as actualdelivery_status
inner join delivery_status 
	on actualdelivery_status.delivery_id = delivery_status.delivery_id
	and actualdelivery_status.date_time = delivery_status.date_time
    and delivery_status.status_id=3
inner join deliveries on deliveries.id = actualdelivery_status.delivery_id
		and CASE
			WHEN 0 > 0 THEN deliveries.user_id = 1
			ELSE true
			END;


select * from deliveries
join users on user_id=users.id;

select * from deliveries
where user_id=1;

delete from regions where id=2;

select count(*) from deliveries
join delivery_status on deliveries.id=delivery_id
where status_id=5
and case 
when 1>0 
then user_id=1 
else true
end;

select * from deliveries;
delete from deliveries where id=1;

update deliveries
set origin_city_id=277, destination_city_id=74,
adress=111, cargo_type=3, volume=3, cost=33
where id=15;

truncate delivery_status;
truncate deliveries;

