defmodule Caht.Application do
  # See https://hexdocs.pm/elixir/Application.html
  # for more information on OTP Applications
  @moduledoc false

  use Application

  def start(_type, _args) do
    children = [
      # Start the Ecto repository
      Caht.Repo,
      # Start the Telemetry supervisor
      CahtWeb.Telemetry,
      # Start the PubSub system
      {Phoenix.PubSub, name: Caht.PubSub},
      # Start the Endpoint (http/https)
      CahtWeb.Endpoint
      # Start a worker by calling: Caht.Worker.start_link(arg)
      # {Caht.Worker, arg}
    ]

    # See https://hexdocs.pm/elixir/Supervisor.html
    # for other strategies and supported options
    opts = [strategy: :one_for_one, name: Caht.Supervisor]
    Supervisor.start_link(children, opts)
  end

  # Tell Phoenix to update the endpoint configuration
  # whenever the application is updated.
  def config_change(changed, _new, removed) do
    CahtWeb.Endpoint.config_change(changed, removed)
    :ok
  end
end
