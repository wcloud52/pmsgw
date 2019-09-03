>��װ flow ����

```
npm install --save-dev flow-bin
```

> ��װ webpack �� flow ���

```
npm install --save-dev flow-webpack-plugin
```

��Ȼ�� webpack û���� flow

> ���babel �� flow �����Ĭ�����Ѿ�װ��babel��

```
npm install --save-dev babel-preset-flow
```

>.babelrc�ļ����

```
"presets": [
    "flow"
]
```

>webpack.config.js ����ӣ�

```
// ����
var FlowWebpackPlugin = require('flow-webpack-plugin');

// �������ӵ������
plugins: [
    new FlowWebpackPlugin({
      flowArgs: ['status']
    })
]
```

ע��status ��ȽϿ죬 check ��Ƚ�����

>����� node_modules �ļ���

�޸��ļ��� ``/.flowconfig``�����û������ļ����ǵ����� ``flow init`` ����һ�£�����ֱ������ flow �������Ҫȫ��װһ�� flow ���У�

```
[ignore]
/node_modules
```

Ӧ��װ���˰ɡ�����

<h3>ʹ�÷���</h3>

```
/* @flow */
// @flow  

�������������ԣ�ֻҪ�������ע�ͣ�����������ͼ��

����
/* @flow weak */ ֻ���м�����ע��ı����������ͼ��
```

<b>ע�⣺</b>

����� window �¹ҵ�ȫ�ֱ���������document֮�࣬����ͨ�� window.document �����ò��У�����ᱨ��

���磺

```
var DOM = document.createElement('div')
DOM.innerHTML = '123'

// ��ȷ
window.document.body.appendChild(DOM)

// ���󣨱���
document.body.appendChild(DOM)
```

