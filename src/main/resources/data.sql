INSERT INTO products (name, price, description, stock, category)
SELECT 'コーヒーメーカー', 12000, '高品質なコーヒーメーカーです。豊かな香りを楽しめます。', 50, '家電'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'コーヒーメーカー');

INSERT INTO products (name, price, description, stock, category)
SELECT 'ワイヤレスイヤホン', 8000, 'ノイズキャンセリング対応の高音質イヤホン。最大30時間再生。', 100, '家電'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'ワイヤレスイヤホン');

INSERT INTO products (name, price, description, stock, category)
SELECT '水筒', 3000, '保温・保冷対応の500ml水筒。軽量設計でアウトドアにも最適。', 200, '日用品'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = '水筒');
