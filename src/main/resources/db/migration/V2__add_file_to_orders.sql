-- Add file column to orders table
ALTER TABLE orders
ADD COLUMN file_data LONGBLOB; 