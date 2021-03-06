
INIT_AT = Time.now.to_f

require 'rubygems'
require 'java'


Dir.glob('../libs/swt*/*.jar') do |jar|
  require jar
end

require 'SWTBotUtils.jar'

require 'swt_wrapper'

import com.handlino.swtbot.patch.SWTBotUtils
#print SWTBotUtils

module SwtDemo
  extend self

  def run
    puts 'run SwtDeom OK, spend '+(Time.now.to_f - INIT_AT).to_s
    while(!@shell.is_disposed) do
      @display.sleep if(!@display.read_and_dispatch) 
    end
    @display.dispose
  end

  def create_image(path)
    Swt::Graphics::Image.new( Swt::Widgets::Display.get_current, java.io.FileInputStream.new( File.join('..', 'images', path)))
  end

  def update_menu_position_handler ()
    Swt::Widgets::Listener.impl do |method, evt|
      @menu.visible = true
    end
  end

  def default_menu_handler(name)
    Swt::Widgets::Listener.impl do |method, evt|
      puts '***Click '+name.to_s
    end
  end

  def add_menu_item(label, selection_handler = nil, item_type =  Swt::SWT::PUSH, menu = nil, index = nil)
    menu = @menu unless menu
    if index
      menuitem = Swt::Widgets::MenuItem.new(menu, item_type, index)
    else
      menuitem = Swt::Widgets::MenuItem.new(menu, item_type)
    end

    menuitem.text = label
    if selection_handler
      menuitem.addListener(Swt::SWT::Selection, selection_handler ) 
    else
      #menuitem.enabled = false
      menuitem.addListener(Swt::SWT::Selection, default_menu_handler(label) ) 
    end
    menuitem
  end


  def test_gui
    SWTBotUtils.findSwtBotMenuByMenu(@menu, "Q1").click();
    SWTBotUtils.findSwtBotMenuByMenu(@menu, "Q2").click();
    SWTBotUtils.findSwtBotMenuByMenu(@menu, "Q2-3").click();
  end


  @display = Swt::Widgets::Display.get_current
  @shell = Swt::Widgets::Shell.new( @display, Swt::SWT::ON_TOP | Swt::SWT::MODELESS)

  @tray_item = Swt::Widgets::TrayItem.new( @display.system_tray, Swt::SWT::NONE)
  @tray_item.image = create_image('icon.png')
  @tray_item.tool_tip_text = "Swt-Demo-on-jruby"
  @tray_item.addListener(Swt::SWT::MenuDetect,  update_menu_position_handler)


  @menu = Swt::Widgets::Menu.new(@shell, Swt::SWT::POP_UP)
  @Q1 = add_menu_item( "Q1")
  @Q2 = add_menu_item( "Q2", nil, Swt::SWT::CASCADE)
  @Q2.menu = Swt::Widgets::Menu.new(@menu)
  @Q2_1 = add_menu_item( "Q2-1", nil, Swt::SWT::PUSH, @Q2.menu)
  @Q2_2 = add_menu_item( "Q2-2", nil, Swt::SWT::PUSH, @Q2.menu)
  @Q2_3 = add_menu_item( "Q2-3", nil, Swt::SWT::PUSH, @Q2.menu)
  @Q3 = add_menu_item( "Q3")
  #@menu.visible = true

  class QuietLayout < org.apache.log4j.PatternLayout
    def format(event)
      ""
    end
  end

  appender = org.apache.log4j.ConsoleAppender.new QuietLayout.new
  org.apache.log4j.BasicConfigurator.configure(appender);


  test_gui
  



  run

end