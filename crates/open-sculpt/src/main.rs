fn main() -> Result<(), winit::error::EventLoopError> {
    let event_loop = winit::event_loop::EventLoop::builder().build()?;

    event_loop.set_control_flow(winit::event_loop::ControlFlow::Poll);

    let mut app = core::App::default();
    event_loop.run_app(&mut app)?;

    Ok(())
}
