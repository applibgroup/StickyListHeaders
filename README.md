# StickyListHeaders

StickyListHeaders makes it easy to integrate section headers in your ListContainer.These section headers stick to the top. The goal of this project is to deliver a high performance replacement to ListContainer.You should with minimal effort and time be able to add section headers to a list.
This should be done via a simple to use API without any special features.

# StickyListHeaders includes :
* Displays the section headers in your ListContainer.
* Displays section headers stick to the top.
* Supports customized background color to sticky header background, header text and list container background.

# Usage Instructions
The following core classes are the essential interface to StickyListHeaders:

StickyListHeadersAdapter: Transaction item provider for list container.

```
	public class StickyListHeadersAdapter extends BaseItemProvider

```

# StickyListHeadersAdapter.java
-----
public Component getComponent(int position, Component convertView, ComponentContainer componentContainer) {
    Component component = convertView;
    if (component == null) {
        component = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_list_item, null, false);
    }

    String itemName = mData.get(position).getName();
    boolean isHeader = mData.get(position).isHeader();

    Text code = (Text) component.findComponentById(ResourceTable.Id_item_name);
	code.setBackground(customElement(isHeader));     
    code.setText(itemName);

    return component;
}

StickyModel: Model class for sticky list header name.

```
	public class StickyModel

```

ViewUtil: Util class to set custom element for header text.

```
	public class ViewUtil

```

LogUtil: LogUtil is intended to be use for add log messages.

```
	public class LogUtil

```

The steps to initialize the StickyListHeaders and example:
   
    StickyListHeadersAdapter listItemProvider = new StickyListHeadersAdapter(this, mStickyList);
    listItemProvider.setResource(ResourceTable.Layout_list_item, ResourceTable.Id_item_name);
    listItemProvider.setColor(listItemProvider.DEFAULT_HEADER_BG_COLOR, listItemProvider.DEFAULT_LIST_BG_COLOR);
    mListContainer.setItemProvider(listItemProvider);

    Text headerName = (Text) mView.findComponentById(ResourceTable.Id_header_name);
    headerName.setBackground(ViewUtil.customElement(ViewUtil.DEFAULT_HEADER_TEXT_COLOR));
    headerName.setClickedListener(this);

    mListContainer.setScrolledListener(this);

    super.setUIContent(mView);
	
Please refer the sample app which has StickyListHeaders component on top of list component.
To use StickyListHeaders in your own ohos project, simply copy `stickylistheaders.har` (available from this repository's package) into your project's `/libs` directory and add it to the build path.


# Installation Instructions

1.For using StickyListHeaders module in sample app, Add the dependencies in entry/build.gradle as below :

		dependencies {
			implementation project(path: ':library')
		}

2.Using the library.har, make sure to add library.har file in the entry/libs folder.

	Modify the dependencies in the entry/build.gradle file.
		dependencies {
			implementation fileTree(dir: 'libs', include: [' *.jar', ' *.har'])
		}

3. For using StickyListHeaders from a remote repository in separate application, add the below dependencies  :

    	Modify entry build.gradle as below :
    	```
    	dependencies {
    	    implementation 'io.openharmony.tpc.thirdlib:StickyListHeaders:1.0.1'
    	}
        ```
