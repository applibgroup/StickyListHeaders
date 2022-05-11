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

package se.emilsjolander.stickylistheaders.sample.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.Text;
import ohos.agp.components.ListContainer;
import ohos.agp.utils.Color;
import ohos.global.resource.Element;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.agp.components.LayoutScatter;
import se.emilsjolander.stickylistheaders.LogUtil;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyModel;
import se.emilsjolander.stickylistheaders.ViewUtil;
import se.emilsjolander.stickylistheaders.sample.ResourceTable;
import se.emilsjolander.stickylistheaders.sample.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TestAbilitySlice extends AbilitySlice implements Component.ClickedListener, Component.ScrolledListener
 */
public class TestAbilitySlice extends AbilitySlice implements Component.ClickedListener, Component.ScrolledListener {
    private static final String TAG = TestAbilitySlice.class.getName();

    private static final byte START_INDEX = 0;

    private static final byte FIRST_POSITION = 1;

    private String itemType = null;

    private ComponentContainer mView;

    private Text mHeaderName;

    private ListContainer mListContainer;

    private List<StickyModel> mStickyList;


    @Override
    public void onStart(Intent intent) {
        mView = (ComponentContainer) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_main, null, false);
        if (mView.findComponentById(ResourceTable.Id_sample_list) instanceof ListContainer) {
            mListContainer = (ListContainer) mView.findComponentById(ResourceTable.Id_sample_list);
        }

        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_main);
        initView();
        setProvider();
    }

    /**
     * Initialise views.
     */
    private void initView() {
        String[] countryArray = new String[START_INDEX];
        try {
            countryArray = getElement().getStringArray();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, "Exception occurred");
        }
        List<String> dataList = new ArrayList<>();

        for (int pos = FIRST_POSITION; pos <= countryArray.length; pos++) {
            if (pos == FIRST_POSITION) {
                dataList.add(START_INDEX, countryArray[START_INDEX]);
                dataList.add(pos, countryArray[START_INDEX]);
                continue;
            }
            dataList.add(pos, countryArray[pos - FIRST_POSITION]);
        }

        Character lastFirstItem = dataList.get(START_INDEX).charAt(START_INDEX);

        mStickyList = new ArrayList<>();
        for (int pos = START_INDEX; pos < dataList.size(); pos++) {
            StickyModel sampleModel = new StickyModel();
            if (pos == START_INDEX) {
                sampleModel.setName(String.valueOf(dataList.get(pos).charAt(START_INDEX)));
                sampleModel.setHeader(true);
                mStickyList.add(sampleModel);
                continue;
            }
            Character currentItemChar = dataList.get(pos).charAt(START_INDEX);
            if (!lastFirstItem.equals(currentItemChar)) {
                sampleModel.setName(String.valueOf(currentItemChar));
                sampleModel.setHeader(true);
                mStickyList.add(sampleModel);
                lastFirstItem = dataList.get(pos).charAt(START_INDEX);
                pos = pos - FIRST_POSITION;
            } else {
                sampleModel.setName(dataList.get(pos));
                sampleModel.setHeader(false);
                mStickyList.add(sampleModel);
            }
        }
    }

    /**
     * Test case - StickyListHeader with default red color to header text, header background and list background.
     */
    private void setProvider() {
        StickyListHeadersAdapter listItemProvider = new StickyListHeadersAdapter(this, mStickyList, new StickyListHeadersAdapter.OnItemClicklistener() {
            @Override
            public void onItemClick(String selectedItem, boolean isHeader) {
                if (isHeader) {
                    itemType = "Header " + selectedItem;
                } else {
                    itemType = "Item " + selectedItem + " clicked!";
                }
                Util.showTips(getContext(), itemType);
            }
        });

        listItemProvider.setResource(ResourceTable.Layout_list_item, ResourceTable.Id_item_name);
        listItemProvider.setColor(StickyListHeadersAdapter.DEFAULT_HEADER_BG_COLOR,
                StickyListHeadersAdapter.DEFAULT_LIST_BG_COLOR);
        mListContainer.setItemProvider(listItemProvider);

        if (mView.findComponentById(ResourceTable.Id_header_name) instanceof Text) {
            mHeaderName = (Text) mView.findComponentById(ResourceTable.Id_header_name);
            mHeaderName.setBackground(ViewUtil.customElement(ViewUtil.DEFAULT_HEADER_TEXT_COLOR));
            mHeaderName.setClickedListener(this);
        }
        mListContainer.setScrolledListener(this);
        super.setUIContent(mView);
    }

    @Override
    public void onContentScrolled(Component component, int index, int i1, int i2, int i3) {
        int position = mListContainer.getItemPosByVisibleIndex(START_INDEX);
        mHeaderName.setText(mStickyList.get(position).getName().substring(START_INDEX, FIRST_POSITION));
        mHeaderName.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(Component component) {
        if (component.getId() == ResourceTable.Id_header_name) {
            itemType = "Header currentlySticky ? True ";
            Util.showTips(getContext(), itemType);
        }
    }

    private Element getElement() {
        Element element = null;
        try {
            element = getResourceManager().getElement(ResourceTable.Strarray_countries);
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, "Exception occurred");
        }
        return element;
    }
}