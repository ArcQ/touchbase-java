defmodule CahtWeb.PageController do
  use CahtWeb, :controller

  def index(conn, _params) do
    render(conn, "index.html")
  end
end
