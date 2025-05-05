#[allow(dead_code)]
#[cfg(target_os = "android")]
#[unsafe(no_mangle)]
fn android_main(app: winit::platform::android::activity::AndroidApp) {
    use winit::platform::android::EventLoopBuilderExtAndroid;

    android_logger::init_once(
        android_logger::Config::default().with_max_level(log::LevelFilter::Trace),
    );

    let event_loop = winit::event_loop::EventLoop::<winit::platform::android::activity::AndroidApp>::with_user_event()
        .with_android_app(app)
        .build()
        .unwrap();

    event_loop.set_control_flow(winit::event_loop::ControlFlow::Poll);

    let mut open_sculpt = core::App::<winit::platform::android::activity::AndroidApp>::new();
    event_loop.run_app(&mut open_sculpt).unwrap();
}
