/*
 * ============================================================================
 * Project betoffice-wrapper Copyright (c) 2000-2022 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.wrapper.impl;

import java.util.Objects;

import de.betoffice.wrapper.api.Result;

class DefaultResult<T> implements Result<T> {

    private final Throwable exception;
    private final T result;

    private DefaultResult(T result, Throwable exception) {
        this.result = result;
        this.exception = exception;
    }

    static <T> Result<T> success(T result) {
        Objects.nonNull(result);
        return new DefaultResult<T>(result, null);
    }

    static <T> Result<T> failure(Throwable exception) {
        Objects.nonNull(exception);
        return new DefaultResult<T>(null, exception);
    }

    @Override
    public T result() {
        return result;
    }

    @Override
    public Throwable exeption() {
        return exception;
    }

    @Override
    public boolean success() {
        return exception == null && result != null;
    }
}
