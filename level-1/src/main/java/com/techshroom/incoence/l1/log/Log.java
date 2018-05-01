package com.techshroom.incoence.l1.log;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

public class Log {

    public static Logger currentClassLogger() {
        List<StackTraceElement> stack = Throwables.lazyStackTrace(new Throwable());
        return LoggerFactory.getLogger(stack.get(1).getClassName());
    }

}
