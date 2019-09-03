<h3>19���̳У���ʵ�ϻ�����ϣ���չ�ֵ�Ч�����Ǽ̳У�</h3>

������ʱ�����������һ�������

1. ��һ������
2. �����е��������input�������������textarea����Ҳ���������ı��絥ѡ�򣬻��ѡ��
3. ��������ʽ��ͳһ�ģ�����ϣ��ͳһ����
4. ��ÿ����������֤�߼���ͬ��
5. ÿ����������Լ��Ĵ�����ʾ��

����������£���������ô����ģ�

1. ��ÿ������򵥶���ֳ������Ȼ���������������Щ�����
2. ���� css����Ϊ��ʽ��ͬ�����Բ��õ���ͳһ�� class����˿�����Ҫ����Щ��ʽ����д��ĳ��css�ļ���Ȼ������Щ��ʽ��������벢ʹ����Щ�ࣻ
3. ��ÿ�������д��ʽ���߼��ȣ�
4. ��������ʽ��������ͬ���߼���������Ҫ����ճ�����Ż���ʽ�ǽ���Щ��֤�߼���������һ��js�ļ���Ȼ������Щ������������js�ļ�����ʹ����Щ�߼�����

ʾ���ԡ�

����д����������˵������һ����Ŀ��˵��Ҳ�㹻�ˣ��Ż��̶�Ҳ���

���Ͼ����и��õ�д��������˼·��

1. �����Ϊ�����������չ�����
2. ������������������ʽ��HTML Ԫ�ء���������֤�߼���һЩͨ�õ��߼��ȣ�����ͨ�ô�����ʾ����
3. ��չ��������û������������ ����ֵ�����������������������һ����ƣ�����֤�߼�����Ȼ�������绰��������������ǵ���֤�߼��ǲ�ͬ�ģ�����ʾ��Ϣ������ HTML ��ǩ�� title ���ԣ��ȣ�
4. ����������չ�����
5. ����ζ�ţ�������ʹ�õ�ʱ��ֻ��Ҫ����չ������������������ɣ�
6. ��д��չ���ʱ��ֻ��Ҫר�����߼�������Ҫ������ʽ��ͨ�������⣻
7. ��д���������ʱ��ֻ��Ҫ���Ĺ��ԣ�������Ҫ�����߼������߼���ʹ���߼���û���߼���ִ�пպ������ɣ�

�����루��β�����ͣ���

```
// �������
class BaseInput extends React.Component {
    render() {
        let left = {display: 'inline-block', width: '100px'}
        let right = {display: 'inline-block', width: '200px', boxSizing: 'border-box'}
        let DOM
        let changeFn
        if (this.props.onChange) {
            changeFn = e => {
                this.props.onChange(e)
            }
        } else {
            changeFn = () => {
            }
        }
        // ���ȣ���������ѡ����Ҫ�������
        if (this.props.type === 'input' | !this.props.type) {
            DOM = <span>
                <span style={left}>{this.props.label}</span>
                <input style={right} type="text"
                       onChange={changeFn}
                       value={this.props.value}/>
            </span>
        } else if (this.props.type === 'textarea') {
            DOM = <span>
                <span style={left}>{this.props.label}</span>
                <textarea style={right} type="text" onChange={changeFn}
                          value={this.props.value}/>
            </span>
        }
        return <div style={{height: '50px'}}>
            {DOM}
            {/* ��Σ��������ⲹ��������ӵ����� */}
            {this.props.children}
        </div>
    }
}

class ChineseName extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: ''
        }
        this.verification = this.verification.bind(this)
    }

    verification(e) {
        let v = e.target.value
        // �����������ַ�
        if (/[\u4e00-\u9fa5]/.test(v)) {
            this.setState({
                value: v
            })
        }
    }

    render() {
        return <BaseInput label={'����'}
                          type={'input'}
                          value={this.state.value}
                          onChange={this.verification}>
            <span style={{color: 'red'}}>ֻ�������������ַ�</span>
        </BaseInput>
    }
}

class EnglishName extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            value: ''
        }
        this.verification = this.verification.bind(this)
    }

    verification(e) {
        let v = e.target.value
        // ֻ����Ӣ����ĸ�Ϳհ׷�
        if (!/[^a-zA-Z\s]/.test(v)) {
            this.setState({
                value: v
            })
        }
    }

    render() {
        return <BaseInput label={'Ӣ����'}
                          type={'input'}
                          value={this.state.value}
                          onChange={this.verification}>
            <span style={{color: 'red'}}>ֻ��������a~z������A~Z�����߿հ��ַ�</span>
        </BaseInput>
    }
}

class TextareaInput extends React.Component {
    render() {
        return <BaseInput label={'�������'} type={'textarea'}>
            <span style={{color: 'red'}}>�������д</span>
        </BaseInput>
    }
}


ReactDOM.render(
    <div>
        <ChineseName/>
        <EnglishName/>
        <TextareaInput/>
    </div>,
    document.getElementById('root')
)
```

˵����

1. BaseInput �ǻ�������������𿪷Žӿڡ�������չ���ֻ��Ҫ��������������Щ�ӿڣ�Ȼ��ʹ�ü��ɣ�
2. ChineseName ����չ���֮һ���ǻ������������ʵ������
3. ͨ�� props.type ���߻�������������������ѡ��ʹ����һ�����͵� HTML Ԫ�أ�input ���� textarea����
4. ͨ�� props.value ��ֵ�������������������������ָ�ŵ�ָ����λ�ã�
4. ������֤���� ``verification`` �������������������������ʲôʱ���������
5. ͨ�� props.children ���߻����������Ҫ��һЩ��Դ����չ����� HTML Ԫ�ز��룬�������������������뵽���

���õ��Ż�������

1. �����������������Ӹ��ӣ����Խ���������ٲ�֣�
2. ������Ϊ MyInput ����� MyTextArea �����
3. �������ר���ڻ�������������ʽ���Լ�һЩͨ���߼����Լ� props.children ����������ȣ�
4. ϸ�ֺ�� MyInput ���������ר���ڸ㶨 input ��������ʽ��ͨ���߼��ȣ�
