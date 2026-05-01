A Simple Api using Java with spring.
based on Fernanda kipper guide
using Supabase with postgres as database.


Setup

in "src/main/resources"

rename "application.properties-sample" to "application.properties" 
and then set the following environment variables needed in the file


  Database releated variables 

DB_URL
DB_USERNAME
DB_PASSWORD
DB_DRIVER_CLASS_NAME
S3_REGION
S3_ENDPOINT

Image Storage releated variables

S3_ACCESS_KEY
S3_SECRET_KEY
S3_BUCKET_NAME
S3_SUPABASE_URL

using supabase bucket storage, but any s3 based software would also work


