INSERT INTO sales_status (name) VALUES 
('在職中');

-- 3-2. engineer_status 데이터 삽입
INSERT INTO engineer_status (name) VALUES 
('勤務中'),
('今月入社'),
('来月入社'),
('今回終了'),
('他');

-- 3-3. engineer_type 데이터 삽입
INSERT INTO engineer_type (name) VALUES 
('正社員'),
('GE'),
('新卒'),
('契約社員');

INSERT INTO users (username,password,name) VALUES ('admin','qwer1234!','admin');