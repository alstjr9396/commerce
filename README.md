# Required

## Command
1. docker-compose up
2. mysql: create database commerce
3. cassandra: create keyspace catalog with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

---

## Library
### Protobuf

- 메시지의 직렬화/역직렬화를 손쉽게 해주는 도구
- 특정 메시지를 자동으로 자바 클래스로 컴파일
- build.gradle


      plugins {
        id 'com.google.protobuf' version '0.9.4'
      }
    
      dependencies {
        implementation 'com.google.protobuf:protobuf-java:3.25.2'
      }
    
      protobuf {
        protoc {
            artifact = 'com.google.protobuf:protoc:3.6.1'
        }
      }
    

- .proto 파일 추가

    
    syntax = "proto3";

    package kafka.protobuf;
    
    message ProductTags {
        int64 product_id = 1;
        repeated string tags = 2;
    }

        