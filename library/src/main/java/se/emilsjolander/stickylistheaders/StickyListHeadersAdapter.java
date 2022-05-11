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
import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.app.Context;

import java.util.List;

/**
 * StickyListHeadersAdapter item provider for list container.
 */
public class StickyListHeadersAdapter extends BaseItemProvider {
    /**
     * DEFAULT_HEADER_BG_COLOR
     */
    public static final int DEFAULT_HEADER_BG_COLOR = Color.RED.getValue();
    /**
     * DEFAULT_LIST_BG_COLOR
     */
    public static final int DEFAULT_LIST_BG_COLOR = Color.WHITE.getValue();

    private int mBgColor;

    private int mHeaderBgColor;

    private Context context;

    private List<StickyModel> mData;

    private int mXmlId;

    private int mComponentId;

    private OnItemClicklistener onItemClicklistener;

    /**
     * OnItemClicklistener interface for click listener
     */
    public interface OnItemClicklistener {
        void onItemClick(String position, boolean isHeader);
    }

    /**
     * Sticky list item provider for list container.
     * This constructor is used to create instance to StickyListHeadersAdapter
     * class to setResource, setColor to sticky list.
     *
     * @param context context
     * @param data    List String
     */
    public StickyListHeadersAdapter(Context context, List<StickyModel> data,
                                    StickyListHeadersAdapter.OnItemClicklistener onItemClicklistener) {
        this.context = context;
        mData = data;
        this.onItemClicklistener = onItemClicklistener;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int index) {
        return mData.get(index);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public Component getComponent(int position, Component convertView, ComponentContainer componentContainer) {
        Component component = convertView;
        if (component == null && mXmlId != 0) {
            component = LayoutScatter.getInstance(context).parse(mXmlId, null, false);
        }

        String itemName = mData.get(position).getName();
        boolean isHeader = mData.get(position).isHeader();

        if (mComponentId != 0) {
            Text text = null;
            if ((component != null) || (component.findComponentById(mComponentId) instanceof Text))  {
                    text = (Text) component.findComponentById(mComponentId);
                    text.setBackground(isCustomElement(isHeader));
                    text.setText(itemName);
                    text.setClickedListener(new Component.ClickedListener() {
                        @Override
                        public void onClick(Component component) {
                            onItemClicklistener.onItemClick(String.valueOf(position), isHeader);
                        }
                    });
            }
        }
        return component;
    }

    /**
     * setResource
     *
     * @param xmlId       integer value
     * @param componentId integer
     */
    public void setResource(int xmlId, int componentId) {
        mXmlId = xmlId;
        mComponentId = componentId;
    }

    /**
     * setColor
     *
     * @param headerBgColor integer value
     * @param bgColor       integer
     */
    public void setColor(int headerBgColor, int bgColor) {
        mBgColor = bgColor;
        mHeaderBgColor = headerBgColor;
    }

    private ShapeElement isCustomElement(boolean isHeader) {
        int color;
        if (isHeader) {
            if (mHeaderBgColor == 0) {
                color = DEFAULT_HEADER_BG_COLOR;
            } else {
                color = mHeaderBgColor;
            }
        } else {
            if (mBgColor == 0) {
                color = DEFAULT_LIST_BG_COLOR;
            } else {
                color = mBgColor;
            }
        }
        ShapeElement style = new ShapeElement();
        style.setRgbColor(RgbColor.fromArgbInt(color));
        return style;
    }
}