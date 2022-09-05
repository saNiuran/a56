package com.niule.a56.calculator.utils.update.fileload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * 作者： Hokas
 * 时间： 2016/7/15
 * 类别：
 */

public class FileResponseBody extends ResponseBody {

    Response originalResponse;

    public FileResponseBody(Response originalResponse) {
        this.originalResponse = originalResponse;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            long bytesReads = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead =super.read(sink, byteCount);
                bytesReads += bytesRead == -1 ? 0:bytesRead;
                RxBus.getDefault().post(new FileLoadingBean(contentLength(),bytesReads));
                return bytesRead;
            }
        });
    }
}
