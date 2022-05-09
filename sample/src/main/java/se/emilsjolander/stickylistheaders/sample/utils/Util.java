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

package se.emilsjolander.stickylistheaders.sample.utils;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.TextAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

/**
 * Util class
 *
 */
public class Util {
    /**
     * set corner radius
     *
     */
    private static final float CORNER_RADIUS = 20.0f;

    /**
     * textsize
     */
    private static final int TEXT_SIZE = 48;

    /**
     * set duration
     */
    private static final int SET_DURATION = 100;

    /**
     * set padding
     */
    private static final int PADDING = 20;

    private Util() {
        /* Do nothing */
    }


    /**
     * showTips
     *
     * @param context ohos Context
     * @param msg Mesage Value
     */
    public static void showTips(Context context, String msg) {
        Text text = new Text(context);
        text.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        text.setHeight(ComponentContainer.LayoutConfig.MATCH_CONTENT);
        text.setTextSize(TEXT_SIZE);
        text.setText(msg);
        text.setPadding(PADDING, PADDING, PADDING, PADDING);
        text.setTextAlignment(TextAlignment.HORIZONTAL_CENTER);
        text.setMarginBottom(200);

        ShapeElement style = new ShapeElement();
        style.setShape(ShapeElement.RECTANGLE);
        style.setCornerRadius(CORNER_RADIUS);
        style.setRgbColor(RgbColor.fromArgbInt(Color.LTGRAY.getValue()));
        text.setBackground(style);

        ToastDialog toastDialog = new ToastDialog(context);
        toastDialog.setDuration(SET_DURATION);
        toastDialog.setAutoClosable(true);
        toastDialog.setAlignment(TextAlignment.BOTTOM);
        toastDialog.setComponent(text);
        toastDialog.show();
    }

}