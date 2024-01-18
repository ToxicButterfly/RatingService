create table rating (
                        id serial not null,
                        rating_score float4,
                        ride_id integer,
                        uid integer,
                        role varchar(255) check (role in ('Passenger','Driver')),
                        primary key (id)
)