﻿using Anmat.Server.Core.Context;
using Xunit;
using System.IO;
using System.Diagnostics;
using System.ServiceModel;
using Anmat.Server.DataService;
using System.ServiceModel.Description;
using System;
using System.ServiceModel.Web;
using System.Threading;
using System.Globalization;

namespace Anmat.Server.Core.IntegrationTests
{
	public class IntegrationSpec
	{
		[Fact]
		public void when_consuming_anmat_data_service_then_succeeds()
		{
			var address = "http://anmatmanager.cloudapp.net/anmatdataservice/AnmatDataService.svc";
			var binding = new WebHttpBinding ();

			binding.MaxReceivedMessageSize = int.MaxValue;

			var channelFactory = new WebChannelFactory<IAnmatDataService> (binding, new Uri(address));

			channelFactory.Endpoint.Behaviors.Add(new WebHttpBehavior());
			
			var channel = channelFactory.CreateChannel ();
			var isNewDataAvailable = channel.IsNewDataAvailable(version: 0);
			var newData = channel.GetData ();

			Assert.True (isNewDataAvailable);
			Assert.NotNull (newData);
		}

		//TODO: Fix this test
		//[Fact]
		public void when_generating_database_from_existing_files_then_succeeds()
		{
			var stopwatch = new Stopwatch ();

			stopwatch.Start ();

			var context = AnmatContext.Initialize ();

			Thread.CurrentThread.CurrentCulture = new CultureInfo(context.Configuration.DefaultCulture);

			var latestVersion = context.VersionService.GetLatestVersion ();

			context.JobService.CreateJob (latestVersion.Number + 1);

			var databaseFileName = context.SQLGenerator.GenerateDatabase (context.DocumentGenerators);
			
			stopwatch.Stop ();

			var script = context.SQLGenerator.Script;

			Debug.WriteLine (string.Format ("ANMAT Data Generation duration: {0} secs", stopwatch.ElapsedMilliseconds / 1000));

			Assert.False(string.IsNullOrEmpty(script));
			Assert.False(string.IsNullOrEmpty(databaseFileName));
			Assert.True (File.Exists (databaseFileName));
		}
	}
}
