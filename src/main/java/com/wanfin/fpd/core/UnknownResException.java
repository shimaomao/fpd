/*
 * Copyright (C) 2012 Stormpath, Inc.
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
package com.wanfin.fpd.core;

/**
 *
 */
public class UnknownResException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnknownResException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public UnknownResException(String s) {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public UnknownResException(String s, Throwable throwable) {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public UnknownResException(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
