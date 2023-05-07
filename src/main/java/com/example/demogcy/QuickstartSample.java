package com.example.demogcy;

// Imports the Google Cloud client library
import com.google.auth.oauth2.ExternalAccountCredentials;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

import java.io.FileInputStream;
import java.util.List;

public class QuickstartSample {

    /** Demonstrates using the Speech API to transcribe an audio file. */
    public static void main(String... args) throws Exception {
//
//
//        ExternalAccountCredentials credentials =
//                ExternalAccountCredentials.fromStream(new FileInputStream("/path/to/credentials.json"));

        // Instantiates a client
        try (SpeechClient speechClient = SpeechClient.create()) {

            // The path to the audio file to transcribe
//            String gcsUri = "gs://cloud-samples-data/speech/brooklyn_bridge.raw";
            String gcsUri="gs://filesave001/e70c6f8f-eda9-48aa-a6a4-fd8cdc6c528b660dd3a9-7c08-4619-945a-2d6ee0c4bfa3.wav";
            // Builds the sync recognize request
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
//                            .setEncoding(AudioEncoding.LINEAR16)
//                            .setSampleRateHertz(16000)
                            .setLanguageCode("en-US")
//            en-US
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
            }
        }
    }
}