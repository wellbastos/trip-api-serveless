package br.com.fiap.handler;

import br.com.fiap.dao.TripRepository;
import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTripBetweenDates implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {
		final String starts = request.getQueryStringParameters().get("start");
		final String ends = request.getQueryStringParameters().get("end");

		context.getLogger().log("Searching for registered trips between " + starts + " and " + ends);

		final List<Trip> trips = this.repository.findByPeriod(starts, ends);

		if (trips == null) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
	}
}