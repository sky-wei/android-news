/*
 * Copyright (c) 2020 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.common.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by sky on 2020-11-28.
 */
public class Execute {


    /**
     * 执行单行或多行命令
     * @param cmd
     * @param commands
     * @return
     */
    public Result exec(String cmd, String... commands) {

        if (TextUtils.isEmpty(cmd)) {
            throw new NullPointerException("命令不能为空!");
        }

        if (commands == null || commands.length <= 0) {
            // 执行单行命令
            return execLine(cmd);
        }

        InputStream inputStream = null;
        InputStream errorStream = null;
        OutputStream outputStream = null;
        Process process = null;

        Result execResult;

        try {
            process = Runtime.getRuntime().exec(cmd);

            inputStream = process.getInputStream();
            errorStream = process.getErrorStream();
            outputStream = process.getOutputStream();

            ByteArrayOutputStream byteInputStream = new ByteArrayOutputStream(1024);
            ByteArrayOutputStream byteErrorStream = new ByteArrayOutputStream(1024);

            new StreamForwarder(inputStream, byteInputStream).start();
            new StreamForwarder(errorStream, byteErrorStream).start();
            new OutputExecute(outputStream, commands).start();

            // 获取返回结果状态
            int code = process.waitFor();

            // 返回执行的结果
            execResult = new Result(code, byteErrorStream.toString(), byteInputStream.toString());
        } catch (Exception e) {
            Alog.e("执行命令异常", e);
            execResult = new Result(e.getMessage());
        } finally {
            FileUtil.closeQuietly(outputStream);
            FileUtil.closeQuietly(errorStream);
            FileUtil.closeQuietly(inputStream);
            if (process != null) process.destroy();
        }
        return execResult;
    }


    /**
     * 只执行单行命令
     * @param cmd
     */
    private Result execLine(String cmd) {

        Process process = null;
        BufferedReader inputStream = null;
        BufferedReader errorStream = null;

        Result execResult;

        try {
            // 执行命令
            process = Runtime.getRuntime().exec(cmd);

            inputStream = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            errorStream = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String line;
            StringBuilder inputString = new StringBuilder();
            StringBuilder errorString = new StringBuilder();

            // 获取返回结果状态
            int code = process.waitFor();

            while ((line = inputStream.readLine()) != null)
                inputString.append(line);

            while ((line = errorStream.readLine()) != null)
                errorString.append(line);

            // 返回执行的结果
            execResult = new Result(code, errorString.toString(), inputString.toString());
        } catch (Throwable e) {
            Alog.e("执行命令异常", e);
            execResult = new Result(e.getMessage());
        } finally {
            FileUtil.closeQuietly(errorStream);
            FileUtil.closeQuietly(inputStream);
            if (process != null) process.destroy();
        }
        return execResult;
    }


    /**
     * 流信息转发
     */
    private static class StreamForwarder extends Thread {

        private final InputStream mInputStream;
        private final OutputStream mOutputStream;

        StreamForwarder(InputStream inputStream, OutputStream outputStream) {
            mInputStream = inputStream;
            mOutputStream = outputStream;
        }

        @Override
        public void run() {
            try {
                int len;
                byte[] buffer = new byte[1024];

                while ((len = mInputStream.read(buffer)) != -1) {

                    mOutputStream.write(buffer, 0, len);
                }
                mOutputStream.flush();
            } catch (IOException e) {
                Alog.e("处理异常", e);
            }
        }
    }


    /**
     * 命令输入
     */
    private static class OutputExecute extends Thread {

        private final OutputStream mOutputStream;
        private final String[] mCommands;

        OutputExecute(OutputStream outputStream, String[] commands) {
            mOutputStream = outputStream;
            mCommands = commands;
        }

        @Override
        public void run() {
            super.run();

            try {
                for (String cmd : mCommands) {

                    final String command = cmd + "\n";

                    mOutputStream.write(command.getBytes());
                    mOutputStream.flush();
                }
            } catch (Exception e) {
                Alog.e("处理异常", e);
            }
        }
    }


    /**
     * 执行的结果类
     */
    public static class Result {

        public final int code;
        public final String errorMsg;
        public final String successMsg;

        Result(String errorMsg) {
            this(-1, errorMsg, "");
        }

        Result(int code, String errorMsg, String successMsg) {
            this.code = code;
            this.errorMsg = errorMsg;
            this.successMsg = successMsg;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "code=" + code +
                    ", errorMsg='" + errorMsg + '\'' +
                    ", successMsg='" + successMsg + '\'' +
                    '}';
        }
    }
}
