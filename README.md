#### ViewOverlay and ViewGroupOverlay

ViewOverlay是View层获取的，ViewGroupOverlay是ViewGroup层获取的，不能直接创建
ViewOverlay和ViewGroupOverlay依附于获取它的View和ViewGroup，
其大小区域与View和ViewGroup是一致的，并且位于View和ViewGroup的上方，
除了展示之外，不具备任何触摸事件，其上可以添加任意的drawable，
视图被约束为只能在其边界内绘制，因此如果覆盖层中的内容延伸出驻留覆盖层的视图的边界，则超出边界的部分会被裁剪。


    ViewOverlay viewOverlay = view.getOverlay();
    ViewGroupOverlay viewGroupOverlay = viewGroup.getOverlay();

##### ViewOverlay

    add(@NonNull Drawable drawable)
    remove(@NonNull Drawable drawable)
    clear()

    ViewOverlay只能添加drawable类型的对象

##### ViewGroupOverlay

    ViewGroupOverlay继承ViewOverlay，在ViewOverlay的基础上又添加了以下两个方法

    add(@NonNull View view)
    remove(@NonNull View view)


> 使用比较简单，场景比较单一，且可以用其他布局替代实现，做简单介绍。
