/*
 * Copyright (C) 2021 Huawei Device Co., Ltd.
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

package se.emilsjolander.stickylistheaders;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

/**
 * Notification ViewUtil
 *
 */
public class ViewUtil {

    private ViewUtil(){
        throw new IllegalStateException("Utility Class");
    }
    /**
     * customElement
     */
    public static final int DEFAULT_HEADER_TEXT_COLOR = Color.RED.getValue();

    /**
     * customElement
     *
     * @param color integer tag
     * @return style
     */
    public static ShapeElement customElement(int color) {
        if (color == 0) {
            color = DEFAULT_HEADER_TEXT_COLOR;
        }
        ShapeElement style = new ShapeElement();
        style.setRgbColor(RgbColor.fromArgbInt(color));
        return style;
    }
}
