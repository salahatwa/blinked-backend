INSERT INTO public.role(
id, active, created_at, description, name)
VALUES (1, true, '2022-12-28 13:23:58.454', 'User role', 'USR');
	
INSERT INTO public.role(
	id, active, created_at, description, name)
	VALUES (2, true, '2022-12-28 13:23:58.454', 'Admin role', 'ADM');
	
	
INSERT INTO public.user_role(
	user_id, role_id)
	VALUES (1, 2);