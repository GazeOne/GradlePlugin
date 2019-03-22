Android Architecture Components

传统方法的弊端：上述写可以实现基础的功能，但是不够灵活，假如除了 ActivityPresenter 类，还有别的类要监听 Activity 生命周期变化，
那也需要添加许多生命周期的回调方法，比较繁琐。那我们是否可以当 Activity 生命周期发生变化的时候主动通知需求方呢？
答案就是使用 Lifecycle 提供的 LifecycleObserver：

