package org.mf.langchain;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public record StreamLanguageModel(StreamingChatLanguageModel streamingChatLanguageModel) {

    public void generate(String prompt, Consumer<String> onNext, Consumer<Throwable> onError, @Nullable Consumer<AiMessage> onCompleted) {
        streamingChatLanguageModel.generate(prompt, new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String s) {
                onNext.accept(s);
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                if(onCompleted != null) onCompleted.accept(response.content());
            }

            @Override
            public void onError(Throwable throwable) {
                onError.accept(throwable);
            }
        });
    }
}
