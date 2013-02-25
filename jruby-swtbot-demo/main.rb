
require 'java'
require 'swt_warpper.rb'



module SwtDemo

  @display = SWT::Widgets::Display.get_current
  @tray_item = Swt::Widgets::TrayItem.new( @display.system_tray, Swt::SWT::NONE)


  def create_image(path)
    Swt::Graphics::Image.new( Swt::Widgets::Display.get_current, java.io.FileInputStream.new( File.join(Main.lib_path, 'images', path)))
  end
  
end