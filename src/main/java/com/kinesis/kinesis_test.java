package com.kinesis;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.kinesis.producer.Attempt;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class kinesis_test {


    public static void main(String[] args) {
        KinesisProducerConfiguration config = new KinesisProducerConfiguration()
                .setRecordMaxBufferedTime(3000)
                .setMaxConnections(1)
                .setRequestTimeout(60000)
                .setRegion("us-west-2")
                .setCredentialsProvider(new EnvironmentVariableCredentialsProvider());

        KinesisProducer kinesis = new KinesisProducer(config);

            ByteBuffer data = null;
            String data_str ="myData";
            List<Future<UserRecordResult>> putFutures = new LinkedList<Future<UserRecordResult>>();
            for (int i = 0; i < 1000000000; i++) {
                try {
                    data = ByteBuffer.wrap((data_str+String.valueOf(i)).getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // doesn't block
                putFutures.add(
                        kinesis.addUserRecord("kinesis_test", data_str.substring(0, 2), data));
                System.out.println("write "+ String.valueOf(i));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Wait for puts to finish and check the results
            for (Future<UserRecordResult> f : putFutures) {
                UserRecordResult result = null; // this does block
                try {
                    result = f.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (result.isSuccessful()) {
                    System.out.println("Put record into shard " +
                            result.getShardId());
                } else {
                    for (Attempt attempt : result.getAttempts()) {
                        // Analyze and respond to the failure
                        System.out.println("wrong");
                    }
                }
        }
    }


}
