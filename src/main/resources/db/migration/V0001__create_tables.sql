CREATE TABLE IF NOT EXISTS public.attractions (
    id int8 NOT NULL primary key ,
    category int4 NULL,
    information varchar(255) NULL,
    latitude float8 NULL,
    longitude float8 NULL,
    middle_rate float8 NULL,
    "name" varchar(255) NULL,
    city_id int4 NULL
);
CREATE TABLE IF NOT EXISTS public.cities (
    id int8 NOT NULL primary key,
    name varchar(255) NOT NULL,
    number int8 NULL
);
CREATE TABLE IF NOT EXISTS public.reviews (
    id int8 NOT NULL,
    rate int4 NULL,
    review varchar(255) NULL,
    attraction_id int8 NULL
);
CREATE TABLE IF NOT EXISTS public.users (
    id        int8 NOT NULL primary key,
    username  varchar(255) NOT NULL,
    name      varchar(255) NOT NULL,
    surname   varchar(255) NOT NULL,
    phone     varchar(255) NULL,
    email     varchar(100) NOT NULL,
    password  varchar(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS public.role (
    id int primary key,
    name varchar(25) NOT NULL
);
CREATE TABLE IF NOT EXISTS public.user_role (
    id int8 primary key,
    user_id int8 NOT NULL
        references public.users(id),
    role_id int8 NOT NULL
        references public.role(id)
);
INSERT INTO public.role VALUES
    (1, 'admin');
INSERT INTO public.role VALUES
    (2, 'user');
