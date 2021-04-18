import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns


def plot_non_executor_web_servers():
    """
    Cohort Exercise 2
    """

    sns.set_theme(style="darkgrid")
    sns.color_palette("bright")
    palette = ["r", "b"]
    fig, ax = plt.subplots(1, 2)
    df = pd.read_csv("non_executor_webservers.csv")
    sns.lineplot(
        x="number_of_clients",
        y="total_time_taken",
        data=df,
        markers=True,
        hue="webserver_type",
        style="webserver_type",
        ax=ax[0],
        palette=palette,
    )
    sns.lineplot(
        x="number_of_clients",
        y="average_throughput",
        data=df,
        markers=True,
        hue="webserver_type",
        style="webserver_type",
        ax=ax[1],
        palette=palette,
    )

    # Label all the data markers/points
    for item, color in zip(df.groupby("webserver_type", sort=False), palette):
        # item[1] is the grouped data frame
        for idx, data in enumerate(
            item[1][
                ["number_of_clients", "total_time_taken", "average_throughput"]
            ].values
        ):
            if item[0] == "thread_per_task" and idx == 0:
                ax[0].text(
                    data[0],
                    data[1],
                    f"({data[0]}, {data[1]})",
                    color=color,
                    fontsize=8,
                    horizontalalignment="left",
                    verticalalignment="top",
                )
                ax[1].text(
                    data[0],
                    data[2],
                    f"({data[0]}, {data[2]})",
                    color=color,
                    fontsize=8,
                    horizontalalignment="right",
                    verticalalignment="top",
                )
            else:
                ax[0].text(
                    data[0],
                    data[1],
                    f"({data[0]}, {data[1]})",
                    color=color,
                    fontsize=8,
                    horizontalalignment="right",
                    verticalalignment="bottom",
                )
                ax[1].text(
                    data[0],
                    data[2],
                    f"({data[0]}, {data[2]})",
                    color=color,
                    fontsize=8,
                    horizontalalignment="right",
                    verticalalignment="bottom",
                )

    ax[0].set(xlabel="Number of clients", ylabel="Total time taken (ms)")
    ax[1].set(xlabel="Number of clients", ylabel="Average throughput (ms/client)")
    plt.show()


def plot_executor_web_servers():
    """
    Cohort Exercise 3
    """

    sns.set_theme(style="darkgrid")
    sns.color_palette("bright")

    palette = ["y", "g", "purple"]
    fig, ax = plt.subplots(1, 2)
    df = pd.read_csv("executor_webservers.csv")
    sns.lineplot(
        x="number_of_clients",
        y="total_time_taken",
        data=df,
        markers=True,
        hue="webserver_type",
        style="webserver_type",
        ax=ax[0],
        palette=palette,
    )
    sns.lineplot(
        x="number_of_clients",
        y="average_throughput",
        data=df,
        markers=True,
        hue="webserver_type",
        style="webserver_type",
        ax=ax[1],
        palette=palette,
    )

    # Label all the data markers/points
    for item, color in zip(df.groupby("webserver_type", sort=False), palette):
        # item[1] is the grouped data frame
        for x, y, z in item[1][["number_of_clients", "total_time_taken", "average_throughput"]].values:
            ax[0].text(
                x,
                y,
                f"({x}, {y})",
                color=color,
                fontsize=8,
                horizontalalignment="right",
                verticalalignment="bottom",
            )
            ax[1].text(
                x,
                z,
                f"({x}, {z})",
                color=color,
                fontsize=8,
                horizontalalignment="right",
                verticalalignment="bottom",
            )

    ax[0].set(xlabel="Number of clients", ylabel="Total time taken (ms)")
    ax[1].set(xlabel="Number of clients", ylabel="Average throughput (ms/client)")
    plt.show()


if __name__ == "__main__":
    print("Plotting the graphs for non-executor-based web servers...")
    plot_non_executor_web_servers()
    print("Plotting the graphs for executor-based web servers...")
    plot_executor_web_servers()