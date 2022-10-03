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

package de.betoffice.wrapper.api;

import java.util.Objects;

public class Scoring {

    private static final GameResult ZERO_ZERO = GameResult.of(0, 0);

    private final GameResult halfTimeResult;
    private final GameResult result;
    private final GameResult overtimeResult;
    private final GameResult penaltyResult;

    private Scoring(GameResult halfTimeResult, GameResult result, GameResult overtimeResult, GameResult penaltyResult) {
        this.halfTimeResult = halfTimeResult;
        this.result = result;
        this.overtimeResult = overtimeResult;
        this.penaltyResult = penaltyResult;
    }

    public static Scoring of(GameResult halfTimeResult, GameResult result) {
        return new Scoring(halfTimeResult, result, ZERO_ZERO, ZERO_ZERO);
    }

    public static Scoring of(GameResult halfTimeResult, GameResult result,
                             GameResult overtimeResult, GameResult penaltyResult) {
        return new Scoring(halfTimeResult, result, overtimeResult, penaltyResult);
    }

    public GameResult getHalfTimeResult() {
        return halfTimeResult;
    }

    public GameResult getResult() {
        return result;
    }

    public GameResult getOvertimeResult() {
        return overtimeResult;
    }

    public GameResult getPenaltyResult() {
        return penaltyResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scoring scoring = (Scoring) o;
        return Objects.equals(halfTimeResult, scoring.halfTimeResult)
                && Objects.equals(result, scoring.result)
                && Objects.equals(overtimeResult, scoring.overtimeResult)
                && Objects.equals(penaltyResult, scoring.penaltyResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(halfTimeResult, result, overtimeResult, penaltyResult);
    }
}
