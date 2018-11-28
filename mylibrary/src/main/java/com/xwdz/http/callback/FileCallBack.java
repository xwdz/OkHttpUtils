package com.xwdz.http.callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author 黄兴伟 (xwdz9989@gamil.com)
 * @since 2018/3/31
 */
public abstract class FileCallBack extends AbstractCallBack<File> {

    /**
     * 目标路径
     */
    private String mPath;
    /**
     * 目标文件名
     */
    private String mFileName;

    private File mFile;

    private int mPercent;

    public FileCallBack(String path, String fileName) {
        this.mPath = path;
        this.mFileName = fileName;
    }


    public FileCallBack(File file) {
        this.mFile = file;
    }


    @Override
    protected File parser(Call call, Response response, boolean isMainUIThread) {
        File file = null;
        try {
            file = saveFile(response);
            final File finalFile = file;
            post(new Runnable() {
                @Override
                public void run() {
                    onFinish(finalFile);
                }
            }, isMainUIThread);
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(call, e);
        }
        return file;
    }

    private File saveFile(Response response) throws IOException {
        InputStream is = null;
        File resultFile = null;
        FileOutputStream fos = null;

        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            if (mFile == null) {
                File dir = new File(mPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                resultFile = new File(dir, mFileName);
            } else {
                resultFile = mFile;
            }
            fos = new FileOutputStream(resultFile);

            int len = 0;
            byte[] buffer = new byte[2 * 1024];
            onStart();
            while ((len = is.read(buffer)) != -1) {
                sum += len;
                fos.write(buffer, 0, len);
                float length = sum * 1.0f / total;
                int percent = (int) (length * 100);

                if (isControlCallback(percent)) {
                    mPercent = percent;
                    onProgressListener(percent, sum, total);
                }
            }
            return resultFile;
        } finally {
            try {
                response.body().close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 控制回调，一定是下载进度大于1才回调上层
     *
     * @param percent 下载百分比
     */
    protected boolean isControlCallback(int percent) {
        return ((percent - mPercent) >= 1);
    }

    protected abstract void onProgressListener(int percent, long currentLength, long total);

    protected abstract void onFinish(File file);

    protected abstract void onStart();
}
