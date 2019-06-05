package com.xwdz.http.rx;

import com.xwdz.http.Convert;
import com.xwdz.http.model.Parser;

import java.io.IOException;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import okhttp3.Response;
/**
 * @author xingwei.huang (xwdz9989@gmail.com)
 * @since v1.0.0
 */
public class RxGsonAdapter<T> implements Convert<T, Response> {

    private Subject subject = PublishSubject.<T>create().toSerialized();

    private Parser mParser;


    public RxGsonAdapter() {
        mParser = Parser.getInstance();
    }

    public <T> T parser(String json) {
        return mParser.parser(json, mParser.getSuperclassTypeParameter(getClass()));
    }

    public Subject getRxResult() {
        return subject;
    }

    @Override
    public T convert(Response response) throws IOException {
        final String json = response.body().string();
        return mParser.parser(json, mParser.getSuperclassTypeParameter(getClass()));
    }
}
