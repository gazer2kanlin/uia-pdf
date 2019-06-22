GridBagView
===

## 設計
布局配置範例：
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<layout xmlns="http://gridbag.pdf.uia/layout">
	<gridbag name="title" x="0" y="0" width="100%" height="30" borderEnabled="false">
		<columns>
			<column width="100%"></column>
		</columns>
		<rows>
			<row height="100%">
				<bindCell fontSize="20">
					<id>titleInfo</id>
				</bindCell>
			</row>
		</rows>
	</gridbag>
	<gridbag name="header" x="0" y="+" width="100%" height="170">
		<columns>
			<column width="15%"></column>
			<column width="@40%"></column>
			<column width="@70%"></column>
			<column width="+100%"></column>
		</columns>
		<rows>
			<row height="30">
				<textCell>
					<text>產品名稱</text>
					<subtext>Product Name</subtext>
				</textCell>
				<textCell></textCell>
				<textCell>
					<text>方法</text>
					<subtext>Method</subtext>
				</textCell>
				<textCell></textCell>
			</row>
			<row height="30" fontSize="12">
				<textCell>
					<text>P/N 號</text>
					<subtext>P/N No.</subtext>
				</textCell>
				<textCell borderSize="1.0" borderColor="0,0,255"></textCell>
				<textCell>
					<text>批號</text>
					<subtext>Lot ID</subtext>
				</textCell>
				<textCell></textCell>
			</row>
			<row height="30">
				<textCell>
					<text>生產效期</text>
					<subtext>Production Date</subtext>
				</textCell>
				<textCell></textCell>
				<textCell fontSize="14">
					<text>效期</text>
					<subtext>Expiry Date</subtext>
				</textCell>
				<textCell></textCell>
			</row>
			<row height="30" background="128,128,255">
				<textCell>
					<text>包裝規格</text>
					<subtext>Package Size</subtext>
				</textCell>
				<textCell></textCell>
				<textCell background="255,0,0">
					<text>銷售</text>
					<subtext>Selling Kit</subtext>
				</textCell>
				<textCell></textCell>
			</row>
			<row height="+100%">
				<textCell>
					<text>檢驗依據</text>
					<subtext>Based On</subtext>
				</textCell>
				<textCell colspan="3"></textCell>
			</row>
		</rows>
	</gridbag>
	<gridbag name="inspection" x="0" y="+" width="100%" height="200">
		<columns>
			<column width="15%"></column>
			<column width="@40%"></column>
			<column width="@55%"></column>
			<column width="@70%"></column>
			<column width="@90%"></column>
			<column width="+100%"></column>
		</columns>
		<rows>
			<row height="25">
				<textCell>
					<text>檢驗項目</text>
					<subtext>Items</subtext>
				</textCell>
				<textCell colspan="3">
					<text>檢驗標準</text>
					<subtext>Standard</subtext>
				</textCell>
				<textCell>
					<text>檢驗結果</text>
					<subtext>Results</subtext>
				</textCell>
				<textCell>
					<text>評價</text>
					<subtext>Comment</subtext>
				</textCell>
			</row>
			<row height="25">
				<textCell rowspan="2">
					<text>淨含量</text>
					<subtext></subtext>
				</textCell>
				<textCell></textCell>
				<textCell></textCell>
				<textCell></textCell>
				<textCell></textCell>
				<textCell></textCell>
			</row>
			<row height="25">
				<textCell></textCell>
				<textCell></textCell>
				<textCell></textCell>
				<textCell></textCell>
				<textCell></textCell>
			</row>
			<row height="25">
				<textCell>
					<text>試劑空白吸光度</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="3"></textCell>
				<textCell></textCell>
				<textCell></textCell>
			</row>
			<row height="25">
				<textCell rowspan="2">
					<text>線性圍範</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="3"></textCell>
				<textCell></textCell>
				<textCell></textCell>
			</row>
			<row height="25">
				<textCell colspan="3"></textCell>
				<textCell></textCell>
				<textCell></textCell>
			</row>
			<row height="+100%">
				<textCell>
					<text>準確度</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="3"></textCell>
				<textCell></textCell>
				<textCell></textCell>
			</row>
		</rows>
	</gridbag>
	<gridbag name="result" x="0" y="+" width="100%" height="200" background="210,210,210">
		<columns>
			<column width="15%"></column>
			<column width="@40%"></column>
			<column width="@55%"></column>
			<column width="@70%"></column>
			<column width="+50%"></column>
			<column width="+100%"></column>
		</columns>
		<rows>
			<row height="25%">
				<textCell>
					<text>結論</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="5"></textCell>
			</row>
			<row height="25%">
				<textCell>
					<text>備註</text>
					<subtext></subtext>
				</textCell>
				<textCell colspan="5"></textCell>
			</row>
			<row height="25%">
				<textCell>
					<text>檢驗人</text>
					<subtext>QC</subtext>
				</textCell>
				<textCell></textCell>
				<textCell>
					<text foreground="0,0,255">審核人</text>
					<subtext foreground="100,100,255">Reviewed By</subtext>
				</textCell>
				<textCell background="255,255,255"></textCell>
				<textCell>
					<text>批准人</text>
					<subtext>Approval By</subtext>
				</textCell>
				<textCell></textCell>
			</row>
			<row height="+100%">
				<textCell alignment="NEAR">
					<text>簽名/日期</text>
					<subtext>Signature/Date</subtext>
				</textCell>
				<textCell></textCell>
				<textCell alignment="FAR">
					<text>簽名/日期</text>
					<subtext>Signature/Date</subtext>
				</textCell>
				<textCell></textCell>
				<textCell>
					<text>簽名/日期</text>
					<subtext>Signature/Date</subtext>
				</textCell>
				<textCell></textCell>
			</row>
		</rows>
	</gridbag>
</layout>
```

## 說明

### <gridbag\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|columns|Column 集合|1|
|v|rows|Row 集合|1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|name|string|名稱|-|
|v|y|string|X 座標|-|
|v|x|string|Y 座標|-|
|v|y|string|高|-|
|v|width|string|寬|-|
|v|height|string|高|-|

### <columns\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|column|Column|1..*|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|width|string|欄寬|-|


### <rows\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|row|Row|1..*|

### <row\>
||Element|Description|Multi|
|:--:|--|--|--|
|v|textCell|文字內容|1..*|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
|v|height|string|欄寬|-|
||fontSize|int|字體大小|-|

### <textCell\>
||Element|Description|Multi|
|:--:|--|--|--|
||text|主要內容|0..1|
||subtext|次要內容|0..1|

||Attribute|Type|Description|Default|
|:--:|--|--|--|--|
||colspan|int|跨欄數|1|
||rowspan|int|跨行數|1|
||borderSize|int|跨行數|0|
||borderColor|int|跨行數|0,0,0|
||fontSize|int|字體大小|-|
